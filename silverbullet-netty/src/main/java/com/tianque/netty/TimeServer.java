package com.tianque.netty;

import com.tianque.netty.decoder.FrameDecoders;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @descripton:
 * @author: mr.0
 * @date: 2018-12-20 10:31
 */
public class TimeServer {

    public static void main(String[] args) {
        int port = 7777;
        if(args!=null&&args.length>0){
            try{
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException e){
            }
        }
        new TimeServer().bind(port);
    }

    /**
     * @Description //TODO
     * @Date 9:29 2018/12/25
     * @Param [port]
     * @return void
     **/
    public void bind(int port){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            FrameDecoders.addDecoder(socketChannel)
                                    .addLast("timeHandler", new TimeServerHandler());
                        }
                    });
            serverBootstrap.bind(port).sync().channel().closeFuture().sync();
        }catch (InterruptedException e){
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

}
