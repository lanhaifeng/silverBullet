package com.tianque.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @descripton:
 * @author: mr.0
 * @date: 2018-12-13 16:11
 */
public class NIOClient {
    public static void main(String[] args) {
        try(SocketChannel socketChannel = SocketChannel.open(); Selector selector  = Selector.open()){
            socketChannel.configureBlocking(false);
            socketChannel.connect( new InetSocketAddress(11111));
            socketChannel.register(selector,SelectionKey.OP_CONNECT);
            while (true){
                selector.select();
                Iterator selectedKeys = selector.selectedKeys().iterator();
                while(selectedKeys.hasNext()){
                    SelectionKey selectionKey1 = (SelectionKey)selectedKeys.next();
                    selectedKeys.remove();
                    if(selectionKey1.isConnectable()){
                        while(!socketChannel.finishConnect()){
                            System.out.println("client is connecting!!!");
                        }
                        selectionKey1.channel().register(selector,SelectionKey.OP_WRITE);
                    }
                    if(selectionKey1.isWritable()){
                        byte[] bytes = new String("<<<<I am standing in front of bench to call u!!!>>>>").getBytes();
                        ByteBuffer buffer = ByteBuffer.wrap(bytes);
                        socketChannel.write(buffer);
                        socketChannel.close();
                    }
                }
            }
        }catch (IOException e){

        }
    }
}
