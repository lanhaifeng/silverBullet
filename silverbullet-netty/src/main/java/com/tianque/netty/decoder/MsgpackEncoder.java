package com.tianque.netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;


/**
 * @descripton: TODO
 * @author: mr.0
 * @since: 2018-12-25 14:38
 * @version: 1.0
 * @see:
 */
public class MsgpackEncoder extends MessageToByteEncoder<Object> {

    /**
     * @Description //TODO
     * @Date 14:45 2018/12/25
     * @Param [ctx, msg, out]
     * @return void
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        byte[] raw = new MessagePack().write(msg);
        out.writeBytes(raw);
    }

}
