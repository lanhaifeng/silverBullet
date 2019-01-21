package com.tianque.netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * @descripton: TODO
 * @author: mr.0
 * @since: 2018-12-25 14:41
 * @version: 1.0
 * @see:
 */
public class MsgpackDecoder extends MessageToMessageDecoder<ByteBuf> {

    /**
     * @Description //TODO
     * @Date 14:49 2018/12/25
     * @Param [ctx, msg, out]
     * @return void
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        final byte[] array;
        final int length = msg.readableBytes();
        array = new byte[length];
        msg.getBytes(msg.readerIndex(),array,0,length);
        out.add(new MessagePack().read(array));
    }

}
