package com.tianque.Lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by QQ on 2018/5/9.
 */
public class RWLockAndCondition {
        Object data;
        volatile boolean cacheValid;
        final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

        void processCachedData() {
            // 获得写锁
            rwl.readLock().lock();
            // 缓存无效，则重写数据
            if (!cacheValid) {
                // 在获得写锁之前，必须先释放读锁
                rwl.readLock().unlock();
                rwl.writeLock().lock();
                try {
                    // 重写检查一次，因为其他线程可能在这段时间里获得了写锁，并且修改了状态
                    if (!cacheValid) {
                        cacheValid = true;
                    }
                    // 在释放写锁之前，通过获取读锁来降级。
                    rwl.readLock().lock();
                } finally {
                    // 释放写锁
                    rwl.writeLock().unlock();
                }
            }
            // cacheValid，直接获取数据，并释放读锁
            try {

            } finally {
                rwl.readLock().unlock();
            }
        }
}
