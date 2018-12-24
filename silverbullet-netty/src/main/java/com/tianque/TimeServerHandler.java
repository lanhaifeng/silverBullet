package com.tianque;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @descripton:
 * @author: mr.0
 * @date: 2018-12-20 10:54
 */
public class TimeServerHandler extends SimpleChannelInboundHandler{

    int counter = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object message) throws Exception {
        System.out.println("server received message:" + counter + message);
        counter ++;
        channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer("Received your message !\n".getBytes()));
    }

//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("RamoteAddress : " + ctx.channel().remoteAddress() + " active !");
//        ctx.writeAndFlush( "Welcome to " + InetAddress.getLocalHost().getHostName() + " service!\n");
//        super.channelActive(ctx);
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
