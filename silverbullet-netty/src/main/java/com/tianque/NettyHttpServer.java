package com.tianque;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.CharsetUtil;

/**
 * @descripton:
 * @author: mr.0
 * @date: 2018-12-20 15:40
 */
public class NettyHttpServer {

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast("http-decoder",new HttpRequestDecoder());
                        //将多个消息转化成一个
                        ch.pipeline().addLast("http-aggregator",new HttpObjectAggregator(65535));
                        ch.pipeline().addLast("http-encoder",new HttpResponseEncoder());
                        //解决大码流的问题
                        ch.pipeline().addLast("http-chunked",new ChunkedWriteHandler());
                        ch.pipeline().addLast("http-server",new HttpHandler());
                    }
                });
        ChannelFuture future = bootstrap.bind("localhost",8888);
        try {
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private static class HttpHandler extends SimpleChannelInboundHandler<FullHttpRequest>{
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
            try {
                ByteBuf content = msg.content();
                byte[] bts = new byte[content.readableBytes()];
                content.readBytes(bts);
                String result = null;
                if(msg.getMethod() == HttpMethod.GET) {
                    String url = msg.getUri().toString();
                    result = "get method and paramters is "+ url.substring(url.indexOf("?")+1);
                }else if(msg.getMethod() == HttpMethod.POST) {
                    result = "post method and paramters is "+ new String(bts);
                }
                FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
                response.headers().set("content-Type","text/html;charset=UTF-8");
                StringBuilder sb = new StringBuilder();
                sb.append("<html>")
                        .append("<head>")
                        .append("<title>netty http server</title>")
                        .append("</head>")
                        .append("<body>")
                        .append(result)
                        .append("</body>")
                        .append("</html>\r\n");
                ByteBuf responseBuf = Unpooled.copiedBuffer(sb, CharsetUtil.UTF_8);
                response.content().writeBytes(responseBuf);
                responseBuf.release();
                ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
