package com.tianque.Lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读锁和写锁是互斥的。
 */
public class RWLockDemo {
    final static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    public static void main(String[] args) {
        new Thread((Runnable)()->{
            read(Thread.currentThread());
        }).start();

        new Thread((Runnable)()->{
            read(Thread.currentThread());
        }).start();
    }

    public static void read(Thread thread){
        lock.readLock().lock();
        try {
            Thread.sleep(10000);
            System.out.println(thread.getName() + ":读操作完毕！");
            System.out.println("end time:"+System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.readLock().unlock();
        }

    }

    public static void write(Thread thread){
        lock.writeLock().lock();
        try {
            Thread.sleep(10000);
            System.out.println(thread.getName() + ":写操作完毕！");
            System.out.println("end time:"+System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.writeLock().unlock();
        }

    }
}
