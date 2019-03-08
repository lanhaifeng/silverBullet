package com.tianque.netty;

import com.tianque.netty.decoder.FrameDecoders;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @descripton:
 * @author: mr.0
 * @date: 2018-12-20 14:02
 */
public class TimeClient {

    public static final String HOST = "127.0.0.1";

    public static void main(String[] args) {
        int port = 7777;
        if(args!=null&&args.length>0){
            try{
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException e){
            }
        }
        new TimeClient().connect(port);
    }

    /**
     * @Description //TODO
     * @Date 10:28 2018/12/25
     * @Param [port]
     * @return void
     **/
    public void connect(int port){
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                 .channel(NioSocketChannel.class)
                 .option(ChannelOption.TCP_NODELAY,true)
                 .handler(new ChannelInitializer<SocketChannel>() {
                     @Override
                     protected void initChannel(SocketChannel socketChannel) throws Exception {
                         FrameDecoders.addDecoder(socketChannel)
                                 .addLast("timeHandler", new TimeClientHandler());
                     }
                 });
            bootstrap.connect(HOST, port).sync().channel().closeFuture().sync();
        }catch (InterruptedException e){
        }finally {
            group.shutdownGracefully();
        }
    }

}
