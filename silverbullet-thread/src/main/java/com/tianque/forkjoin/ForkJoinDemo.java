package com.tianque.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

/**
 * @author linlinan
 * Created by QQ on 2018/5/10.
 * fork/join框架过于复杂，一般不建议直接自己分割任务，建议依赖相关框架
 */
public class ForkJoinDemo {

    public static void main(String[] args) throws Exception {
        // 创建包含Runtime.getRuntime().availableProcessors()返回值作为个数的并行线程的ForkJoinPool
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        // 提交可分解的PrintTask任务
        forkJoinPool.submit(new MyRecursiveAction(0, 200));
        forkJoinPool.awaitTermination(2, TimeUnit.SECONDS);//阻塞当前线程直到 ForkJoinPool 中所有的任务都执行结束
        // 关闭线程池
        forkJoinPool.shutdown();
    }

    static class MyRecursiveAction extends RecursiveAction {
        // 每个"小任务"最多只打印50个数
        private static final int MAX = 50;

        private int start;
        private int end;

        MyRecursiveAction(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {
            // 当end-start的值小于MAX时候，开始打印
            if ((end - start) < MAX) {
                for (int i = start; i < end; i++) {
                    System.out.println(Thread.currentThread().getName() + "的i值:"
                            + i);
                }
            } else {
                // 将大任务分解成两个小任务
                int middle = (start + end) / 2;
                MyRecursiveAction left = new MyRecursiveAction(start, middle);
                MyRecursiveAction right = new MyRecursiveAction(middle, end);
                // 并行执行两个小任务
                left.fork();
                right.fork();
            }
        }
    }

    static class MyRecursiveTask extends RecursiveTask<Long> {

        private long start;
        private long end;
        private static final long THURSHOLD = 10000L;  //临界值

        public MyRecursiveTask(long start, long end) {
            this.start = start;
            this.end = end;
        }
        @Override
        protected Long compute() {
            long length = end - start;
            //小于临界值，则不进行拆分，直接计算初始值到结束值之间所有数之和
            if(length <= THURSHOLD){
                long sum = 0L;

                for (long i = start; i <= end; i++) {
                    sum += i;
                }

                return sum;
            }else{  //大于临界值，取中间值进行拆分，递归调用
                long middle = (start + end) / 2;

                MyRecursiveTask left = new MyRecursiveTask(start, middle);
                left.fork(); //进行拆分，同时压入线程队列

                MyRecursiveTask right = new MyRecursiveTask(middle+1, end);
                right.fork(); //

                return left.join() + right.join();
            }
        }
    }

}
