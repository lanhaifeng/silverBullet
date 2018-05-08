package com.tianque.Lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by QQ on 2018/5/3.
 */
public class LockCondition {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        System.out.println("开始等待");
        condition.wait();

        lock.unlock();
    }
}
