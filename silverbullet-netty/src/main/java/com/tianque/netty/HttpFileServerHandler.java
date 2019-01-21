package com.tianque.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedFile;
import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

/**
 * @descripton: TODO
 * @author: mr.0
 * @since: 2018-12-26 11:08
 * @version: 1.0
 * @see:
 */
public class HttpFileServerHandler  extends SimpleChannelInboundHandler<FullHttpRequest>{

    /**
     * @Description //TODO
     * @Date 13:48 2018/12/26
     * @Param 
     * @return 
     */
    private final String url;

    /**
     * @Description //TODO
     * @Date 13:48 2018/12/26
     * @Param 
     * @return 
     */
    private static final Pattern INSECURE_URI = Pattern.compile(".*[<>&\"].*");

    /**
     * @Description //TODO
     * @Date 13:48 2018/12/26
     * @Param 
     * @return 
     */
    private static final Pattern ALLOWED_FILE_NAME = Pattern.compile("[A-Za-z0-9][-_A-Za-z0-9\\.]*");

    /**
     * @Description //TODO
     * @Date 13:48 2018/12/26
     * @Param [url]
     * @return 
     */
    public HttpFileServerHandler(String url) {
        this.url = url;
    }

    /**
     * @Description //TODO
     * @Date 13:47 2018/12/26
     * @Param [ctx, request]
     * @return void
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx,
                                   FullHttpRequest request) throws Exception {
        if(!request.decoderResult().isSuccess())
        {
            sendError(ctx, HttpResponseStatus.BAD_REQUEST);
            return;
        }
        if(request.method() != HttpMethod.GET)
        {
            sendError(ctx, HttpResponseStatus.METHOD_NOT_ALLOWED);
            return;
        }
        final String uri = request.uri();
        final String path = sanitizeUri(uri);
        if(path == null)
        {
            sendError(ctx, HttpResponseStatus.FORBIDDEN);
            return;
        }
        File file = new File(path);
        if(file.isHidden() || !file.exists())
        {
            sendError(ctx, HttpResponseStatus.NOT_FOUND);
            return;
        }
        if(file.isDirectory())
        {
            if(uri.endsWith("/"))
            {
                sendListing(ctx, file);
            }else{
                sendRedirect(ctx, uri + "/");
            }
            return;
        }
        if(!file.isFile())
        {
            sendError(ctx, HttpResponseStatus.FORBIDDEN);
            return;
        }
        RandomAccessFile randomAccessFile = null;
        try{
            randomAccessFile = new RandomAccessFile(file, "r");
        }catch(FileNotFoundException fnfd){
            sendError(ctx, HttpResponseStatus.NOT_FOUND);
            return;
        }
        long fileLength = randomAccessFile.length();
        HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        HttpUtil.setContentLength(response, fileLength);
        setContentTypeHeader(response, file);
        if(HttpUtil.isKeepAlive(request)){
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }
        ctx.write(response);
        ChannelFuture sendFileFuture = null;
        sendFileFuture = ctx.write(new ChunkedFile(randomAccessFile, 0, fileLength, 8192), ctx.newProgressivePromise());
        sendFileFuture.addListener(new ChannelProgressiveFutureListener() {

            @Override
            public void operationComplete(ChannelProgressiveFuture future)
                    throws Exception {
                System.out.println("Transfer complete.");

            }
            @Override
            public void operationProgressed(ChannelProgressiveFuture future,
                                            long progress, long total) throws Exception {
                if(total < 0){
                    System.err.println("Transfer progress: " + progress);
                }
                else{
                    System.err.println("Transfer progress: " + progress + "/" + total);
                }
            }
        });
        //使用chunked编码时，需要发送一个编码结束的空消息体，标记所有消息体已经发送完成（chunked是分块发送大文件的编码）
        ChannelFuture lastContentFuture = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
        if(!HttpUtil.isKeepAlive(request)){
            lastContentFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }

    /**
     * @Description //TODO
     * @Date 13:47 2018/12/26
     * @Param [ctx, cause]
     * @return void
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        if(ctx.channel().isActive()){
            sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @Description //TODO
     * @Date 13:47 2018/12/26
     * @Param [uri]
     * @return java.lang.String
     */
    private String sanitizeUri(String uri){
        try{
            uri = URLDecoder.decode(uri, "UTF-8");
        }catch(UnsupportedEncodingException e){
            try{
                uri = URLDecoder.decode(uri, "ISO-8859-1");
            }catch(UnsupportedEncodingException e1){
                throw new Error();
            }
        }
        if(!uri.startsWith(url)){
            return null;
        }
        if(!uri.startsWith("/")){
            return null;
        }
        uri = uri.replace('/', File.separatorChar);
        if(uri.contains(File.separator + '.') || uri.contains('.' + File.separator) || uri.startsWith(".") || uri.endsWith(".")
                || INSECURE_URI.matcher(uri).matches()){
            return null;
        }
        return System.getProperty("user.dir") + File.separator + uri;
    }

    /**
     * @Description //TODO
     * @Date 13:47 2018/12/26
     * @Param [ctx, dir]
     * @return void
     */
    private static void sendListing(ChannelHandlerContext ctx, File dir){
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        response.headers()
                .set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=UTF-8");
        String dirPath = dir.getPath();
        StringBuilder buf = new StringBuilder();
        buf.append("<!DOCTYPE html>\r\n");
        buf.append("<html><head><title>");
        buf.append(dirPath);
        buf.append("目录:");
        buf.append("</title></head><body>\r\n");
        buf.append("<h3>");
        buf.append(dirPath).append(" 目录：");
        buf.append("</h3>\r\n");
        buf.append("<ul>");
        buf.append("<li>链接：<a href=\" ../\")..</a></li>\r\n");
        for (File f : dir.listFiles()) {
            if(f.isHidden() || !f.canRead()) {
                continue;
            }
            String name = f.getName();
            if (!ALLOWED_FILE_NAME.matcher(name).matches()) {
                continue;
            }
            buf.append("<li>链接：<a href=\"");
            buf.append(name);
            buf.append("\">");
            buf.append(name);
            buf.append("</a></li>\r\n");
        }
        buf.append("</ul></body></html>\r\n");
        ByteBuf buffer = Unpooled.copiedBuffer(buf, StandardCharsets.UTF_8);
        response.content().writeBytes(buffer);
        buffer.release();
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
    
    /**
     * @Description //TODO
     * @Date 13:46 2018/12/26
     * @Param [ctx, newUri]
     * @return void
     */
    private static void sendRedirect(ChannelHandlerContext ctx, String newUri){
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FOUND);
        response.headers().set(HttpHeaderNames.LOCATION, newUri);
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * @Description //TODO
     * @Date 13:46 2018/12/26
     * @Param [ctx, status]
     * @return void
     */
    private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status){
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status,
                Unpooled.copiedBuffer("Failure: " + status.toString() + "\r\n", StandardCharsets.UTF_8));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html;charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * @Description //TODO
     * @Date 13:46 2018/12/26
     * @Param [response, file]
     * @return void
     */
    private static void setContentTypeHeader(HttpResponse response, File file){
        MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, mimetypesFileTypeMap.getContentType(file.getPath()));
    }
    
}
