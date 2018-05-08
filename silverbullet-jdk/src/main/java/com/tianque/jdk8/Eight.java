package com.tianque.jdk8;

import java.util.concurrent.atomic.LongAdder;

/**
 *@author:linlinan
 *@version:1.0
 *@Date:20180508
 * 更高效的原子计数器（相对于之前用的AtomicLong）
 * LongAdder 沿用了concurrentMap原理，他是将1个整数拆分成一个数组cells，数组中有若干个cell。若有多个线层，每个线程通过CAS更新其中的一个小cell。然后内部将数组做sum求和操作得到整数的value；
这样就使得AtomicLong的单一线程做CAS操作演变成多个线程同时做CAS操作，期间互不影响。从而提高效率；
LongAdder开始并没有做拆分，当多线程间执行遇到冲突时才会拆分cell，若是多线程执行始终没有冲突，则它相当于AtomicLong；
AtomicLong的实现方式是内部有个value 变量，当多线程并发自增，自减时，均通过CAS 指令从机器指令级别操作保证并发的原子性。唯一会制约AtomicLong高效的原因是高并发，高并发意味着CAS的失败几率更高， 重试次数更多，越多线程重试，CAS失败几率又越高，变成恶性循环，AtomicLong效率降低。
而LongAdder将把一个value拆分成若干cell，把所有cell加起来，就是value。所以对LongAdder进行加减操作，只需要对不同的cell来操作，不同的线程对不同的cell进行CAS操作，CAS的成功率当然高了（试想一下3+2+1=6，一个线程3+1，另一个线程2+1，最后是8，LongAdder没有乘法除法的API）。
可是在并发数不是很高的情况，拆分成若干的cell，还需要维护cell和求和，效率不如AtomicLong的实现。LongAdder用了巧妙的办法来解决了这个问题。
初始情况，LongAdder与AtomicLong是相同的，只有在CAS失败时，才会将value拆分成cell，每失败一次，都会增加cell的数量，这样在低并发时，同样高效，在高并发时，这种“自适应”的处理方式，达到一定cell数量后，CAS将不会失败，效率大大提高。
LongAdder是一种以空间换时间的策略。
 **/
public class Eight
{
    private static LongAdder la =new LongAdder();

    public static int a =0;
    public static void add(){
        la.increment();
        a++;
    }
    /**
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO Auto-generated method stub
        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                for(int i=0;i<10000;i++){
                    add();
                }
            }
        });
        t1.start();

        Thread t2 = new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                for(int i=0;i<10000;i++){
                    add();
                }
            }
        });
        t2.start();
        t1.join();t2.join();
        System.out.println("---la-----"+la);
        System.out.println("---a-----"+a);
    }
}
