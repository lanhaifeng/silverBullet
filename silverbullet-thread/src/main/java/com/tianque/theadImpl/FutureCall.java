package com.tianque.theadImpl;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author linlinan
 * @date 20180510
 * future模式，实现callable，并用futuretask封装，可以作为线程的目标类传入线程中调度，这种可以有返回值
 */
public class FutureCall {

    public static void main(String[] args) {
        FutureTask<Object> futureTask = new FutureTask<Object>(new Future());
        Thread thread = new Thread(futureTask);
        thread.start();

        try {
            System.out.println(futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    static class Future implements Callable<Object>{
        @Override
        public Object call() throws Exception {
            Thread.sleep(10000);
            return 1;
        }
    }
}
