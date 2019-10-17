package com.tianque.theadImpl;

/**
 * @author linlinan
 * @date 20180510
 * 实现runnable,其实thread也是runnable的实现类，原理也是调用了run方法
 */
public class ImplRunnable implements Runnable{
    @Override
    public void run() {
        System.out.println("2344");
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new ImplRunnable());
        thread.start();
    }
}
