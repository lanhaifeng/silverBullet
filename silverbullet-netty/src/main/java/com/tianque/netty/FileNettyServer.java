package com.tianque.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @descripton: TODO
 * @author: mr.0
 * @since: 2018-12-26 9:36
 * @version: 1.0
 * @see:
 */
public class FileNettyServer {

    /**
     * @Description //TODO
     * @Date 13:49 2018/12/26
     * @Param 
     * @return 
     */
    private static final String DEFAULT_URL = "/silverbullet-netty/src/main/java/com/tianque/netty";

    /**
     * @Description //TODO
     * @Date 13:49 2018/12/26
     * @Param [port, url]
     * @return void
     */
    public void run(final int port,final String url){
        EventLoopGroup bossLoopGroup = new NioEventLoopGroup();
        EventLoopGroup workerLoopGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            //bossLoopGroup负责accept事件，workerLoopGroup负责读写等事件以及用户task定时task等
            bootstrap.group(bossLoopGroup,workerLoopGroup)
                    //channel()指定服务端的通道类，内部用工厂模式反射生成实例
                    .channel(NioServerSocketChannel.class)
                    //option设置SO_BACKLOG: bossLoopGroup中未链接队列和已连接队列总和的最大值(三次握手中，连接都属于未链接队列；三次握手结束，连接转移到已连接队列)
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    //http消息请求解码器
                                    .addLast("http-decoder",new HttpRequestDecoder())
                                    //将多个消息转换为单一的FullHttpRequest和FullHttpResponse
                                    .addLast("http-aggregator",new HttpObjectAggregator(65536))
                                    //http消息响应编码器
                                    .addLast("http-encode",new HttpResponseEncoder())
                                    //支持异步发送大的码流
                                    .addLast("http-chunked",new ChunkedWriteHandler())
                                    .addLast("fileServerHandler",new HttpFileServerHandler(url));
                        }
            });
            bootstrap.bind(port).sync().channel().closeFuture().sync();
        }catch (InterruptedException e){
        }finally {
            bossLoopGroup.shutdownGracefully();
            workerLoopGroup.shutdownGracefully();
        }
    }

    /**
     * @Description //TODO
     * @Date 13:48 2018/12/26
     * @Param [args]
     * @return void
     */
    public static void main(String[] args) {
        int port = 8888;
        if(args!=null&&args.length>0){
            try {
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException e){
            }
        }
        String url = DEFAULT_URL;
        if(args!=null&&args.length>1){
            url = String.valueOf(args[1]);
        }
        new FileNettyServer().run(port,url);
    }

}
