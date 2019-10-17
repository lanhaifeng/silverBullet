package com.tianque.base;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by QQ on 2018/5/17.
 */
public class TestYeild {
    static AtomicInteger i = new AtomicInteger(1);
    static class YThread implements Runnable{
        @Override
        public void run() {
            System.out.println("thread: "+i.getAndIncrement()+" is running!");
            Thread.yield();
            System.out.println("thread: "+i.intValue()+" is end!");

        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        do{
            executorService.execute(new YThread());
        }while (i.intValue()<100);
    }
}
