package com.tianque.netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.*;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @descripton: TODO
 * @author: mr.0
 * @since: 2018-12-25 10:26
 * @version: 1.0
 * @see:
 */
public class FrameDecoders {

    private static final int mode = 4;

    private static final int maxSize = 100;

    private static final int fixedLen = 100;

    /**
     * @Description //TODO
     * @Date 11:05 2018/12/25
     * @Param []
     * @return java.lang.String
     */
    public static String delimiter() {
        switch (mode){
            case 1 : return "$_";
            default: return "\n";
        }
    }

    /**
     * @Description //TODO
     * @Date 11:09 2018/12/25
     * @Param [socketChannel]
     * @return io.netty.channel.ChannelPipeline
     * @param socketChannel
     */
    public static ChannelPipeline addDecoder(SocketChannel socketChannel){
        switch (mode){
            case 1 : return addDelimiterBasedFrameDecoder(socketChannel);
            case 2 : return addFixedLenthFrameDecoder(socketChannel);
            case 3 : return addLengthFieldBasedFrameDecoder(socketChannel);
            case 4 : return addMsgPackCoder(socketChannel);
            default: return addLineBasedFrameDecoder(socketChannel);
        }
    }

    /**
     * @Description //TODO 按行切换的字符串解码器
     * @Date 9:28 2018/12/25
     * @Param [socketChannel]
     * @return io.netty.channel.ChannelPipeline
     **/
    public static ChannelPipeline addLineBasedFrameDecoder(SocketChannel socketChannel){
        //LineBasedFrameDecoder,需要位于链头，第一个参数表示单条消息最大长度，当达到该长度后仍然没有查找到分隔符，就抛出ToolLongFrameException异常
        return socketChannel.pipeline().addLast("framer", new LineBasedFrameDecoder(maxSize))
                .addLast("decoder", new StringDecoder())
                .addLast("encoder", new StringEncoder());
    }

    /**
     * @Description //TODO 按特殊符号切换的字符串解码器
     * @Date 9:28 2018/12/25
     * @Param [socketChannel]
     * @return io.netty.channel.ChannelPipeline
     **/
    public static ChannelPipeline addDelimiterBasedFrameDecoder(SocketChannel socketChannel){
        ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
        //DelimiterBasedFrameDecoder,需要位于链头，第一个参数表示单条消息最大长度，当达到该长度后仍然没有查找到分隔符，就抛出ToolLongFrameException异常
        return socketChannel.pipeline().addLast("framer", new DelimiterBasedFrameDecoder(maxSize,delimiter))
                .addLast("decoder", new StringDecoder())
                .addLast("encoder", new StringEncoder());
    }

    /**
     * @Description //TODO 按固定长度的字符串解码器
     * @Date 9:28 2018/12/25
     * @Param [socketChannel]
     * @return io.netty.channel.ChannelPipeline
     **/
    public static ChannelPipeline addFixedLenthFrameDecoder(SocketChannel socketChannel){
        //FixedLengthFrameDecoder,需要位于链头，按照固定长度fixedLen截取消息
        return socketChannel.pipeline().addLast("framer", new FixedLengthFrameDecoder(fixedLen))
                .addLast("decoder", new StringDecoder())
                .addLast("encoder", new StringEncoder());
    }

    /**
     * @Description //TODO 消息头添加消息长度的字符串解码器
     * @Date 15:27 2018/12/25
     * @Param [socketChannel]
     * @return io.netty.channel.ChannelPipeline
     */
    public static ChannelPipeline addLengthFieldBasedFrameDecoder(SocketChannel socketChannel){
        //lengthFieldBasedFrameDecoder,需要位于链头，发送数据包长度 = 长度域的值 + lengthFieldOffset + lengthFieldLength + lengthAdjustment
        return socketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(65535,0,2,0,2))
                .addLast("decoder", new StringDecoder())
                .addLast(new LengthFieldPrepender(2))
                .addLast("encoder", new StringEncoder());
    }

    /**
     * @Description //TODO 自定义基于MessagePack的编码解码器
     * @Date 15:27 2018/12/25
     * @Param [socketChannel]
     * @return io.netty.channel.ChannelPipeline
     */
    public static ChannelPipeline addMsgPackCoder(SocketChannel socketChannel){
        //自定义messagepack编码解码器
        return socketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(65535,0,2,0,2))
                .addLast("msgdecoder", new MsgpackDecoder())
                .addLast(new LengthFieldPrepender(2))
                .addLast("msgencoder", new MsgpackEncoder());
    }

}
