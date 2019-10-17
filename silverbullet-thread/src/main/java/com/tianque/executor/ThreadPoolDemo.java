package com.tianque.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author linlinan
 * @Date 20180509
 */
public class ThreadPoolDemo {

    //固定大小的线程，线程数满后，堵塞并等待
    public static void test1(){
        ExecutorService threadService = Executors.newFixedThreadPool(1);

        for (int i = 0; i < 20; i++) {
            final int j = i;
            threadService.execute(()->{
                try {
                    Thread.sleep(4000);
                    System.out.println("当前实现的线程为："+j);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    //固定大小的线程，线程数满后，堵塞并等待
    public static void test2(){
        long start = System.nanoTime();
        ExecutorService threadService = Executors.newFixedThreadPool(20);
        final AtomicInteger j = new AtomicInteger(0);
        for (int i = 0; i < 1000000; i++) {
            threadService.execute(()->{
                    j.incrementAndGet();
            });
        }
        for (;;){
            if(j.intValue()==1000000) break;
        }
        System.out.println("test2 finish");
        long end = System.nanoTime();

        System.out.println("test2 finish waste:"+(end-start));
    }

    //无限大小的线程池，可复用，60s空闲销毁时间
    public static void test3(){
        long start = System.nanoTime();
        ExecutorService threadService = Executors.newCachedThreadPool();
        final AtomicInteger j = new AtomicInteger(0);
        for (int i = 0; i < 1000000; i++) {
            threadService.execute(()->{
                j.incrementAndGet();
            });
        }
        for (;;){
            if(j.intValue()==1000000) break;
        }
        System.out.println("test3 finish");
        long end = System.nanoTime();

        System.out.println("test3 finish waste:"+(end-start));
    }

    //单一线程的线程池
    public static void test4(){
        long start = System.nanoTime();
        ExecutorService threadService = Executors.newSingleThreadExecutor();
        final AtomicInteger j = new AtomicInteger(0);
        for (int i = 0; i < 1000000; i++) {
            threadService.execute(()->{
                j.incrementAndGet();
            });
        }
        for (;;){
            if(j.intValue()==1000000) break;
        }
        System.out.println("test4 finish");
        long end = System.nanoTime();

        System.out.println("test4 finish waste:"+(end-start));
    }

    //有固定大小的可定时延迟的线程池
    //scheduleAtFixedRate: 执行子,第一次延迟时间,两次时间间隔（执行时间大于间隔时间时任务延后开启）,时间单位
    public static void test5(){
        long start = System.nanoTime();
        ScheduledExecutorService threadService = Executors.newScheduledThreadPool(100);
        final AtomicInteger j = new AtomicInteger(0);
        for (int i = 0; i < 1000000; i++) {
            threadService.scheduleAtFixedRate(() -> {j.incrementAndGet();},1000,1000, TimeUnit.MILLISECONDS);
        }
        for (;;){
            if(j.intValue()==1000000) break;
        }
        System.out.println("test4 finish");
        long end = System.nanoTime();

        System.out.println("test4 finish waste:"+(end-start));
    }


    public static void main(String[] args) {
        test2();
        test3();
        test4();
    }
}
