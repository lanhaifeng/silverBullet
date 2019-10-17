package com.tianque.netty;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;

/**
 * @descripton:
 * @author: mr.0
 * @date: 2018-12-13 15:55
 */
public class NIOServer {
    public static void main(String[] args) {
        try(ServerSocketChannel socketChannel = ServerSocketChannel.open(); Selector selector = SelectorProvider.provider().openSelector()){
            socketChannel.configureBlocking(false);
            socketChannel.bind(new InetSocketAddress(11111));
            socketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while(true){
                Set selectedKeys_bef = selector.selectedKeys();
                System.out.println("before select , selectedkeys num:  " + selectedKeys_bef.size());
                System.out.println("server is waiting !!!");
                selector.select();
                System.out.println("socket is in !!!");
                Set selectedKeys = selector.selectedKeys();
                Iterator iterator = selectedKeys.iterator();
                System.out.println("after select , selectedkeys num:  " + selectedKeys.size());
                while(iterator.hasNext()){
                    SelectionKey key = (SelectionKey) iterator.next();
                    if(key.isAcceptable()){
                        System.out.println("one key is acceptable!!");
                        ServerSocketChannel ssc  = (ServerSocketChannel) key.channel();
                        SocketChannel socketChannel1 = ssc.accept();
                        socketChannel1.configureBlocking(false);
                        socketChannel1.register(selector,SelectionKey.OP_READ);
                    }
                    if(key.isReadable()){
                        System.out.println("one key is readable!!");
                        SocketChannel channel  = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocateDirect(2);
                        String content = "";
                        int readBytes = -1;
                        int count = 0;
                        byte[] bytes = null;
                        while ((readBytes = channel.read(buffer))>0) {
                            System.out.println(++count);
                            buffer.flip();
                            bytes = new byte[buffer.remaining()];
                            buffer.get(bytes);
                            content+=new String(bytes);
                            buffer.clear();
                        }
                        System.out.println(content);
                        channel.close();
                    }
                    iterator.remove();
                }
            }
        }catch (IOException e){

        }
    }
}
