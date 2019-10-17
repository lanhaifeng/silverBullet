package com.tianque.netty;

import com.tianque.netty.decoder.FrameDecoders;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @descripton:
 * @author: mr.0
 * @date: 2018-12-20 14:15
 */
public class TimeClientHandler extends SimpleChannelInboundHandler{

    byte[] req;

    public TimeClientHandler(){
        req = ("QUERY　COMMAND" + FrameDecoders.delimiter()).getBytes();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Server say : " + msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client is  active");
        ByteBuf byteBuf = null;
        for(int i = 0 ;i<100; i++){
//            byteBuf = Unpooled.buffer(req.length);
//            byteBuf.writeBytes(req);
            ctx.writeAndFlush("client is send message !~!!!" + FrameDecoders.delimiter());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client is  closed");
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

}
