package com.tianque.theadImpl;

/**
 * @author linlinan
 * @Date 20180508
 */
public class ExtendThread extends Thread{
    @Override
    public void run() {
        System.out.println("123");
    }

    public static void main(String[] args) {
        ExtendThread thread1 = new ExtendThread();
        thread1.start();
    }
}
