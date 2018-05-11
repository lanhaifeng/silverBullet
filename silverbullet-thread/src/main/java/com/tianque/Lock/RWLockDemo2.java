package com.tianque.Lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读锁和写锁是互斥的。
 */
public class RWLockDemo2 {
    final static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    public static void main(String[] args) {
        readUP();
        writeDown();
    }

    //读写锁中，读锁不可直接升级为写锁，必须放开锁
    public static void readUP(){
        lock.readLock().lock();
        lock.writeLock().lock();
        lock.writeLock().unlock();
        lock.readLock().unlock();

        System.out.println("readup ok");
    }
    //读写锁中，写锁可以降级为读锁
    public static void writeDown(){
        lock.writeLock().lock();
        lock.readLock().lock();
        lock.readLock().unlock();
        lock.writeLock().unlock();
        System.out.println("writedown ok");
    }
}
