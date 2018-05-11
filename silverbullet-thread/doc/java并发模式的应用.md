#java并发模式的应用#  
2018/4/28 9:57:25 

----------
本篇分6个内容，分别包括：

- 基本线程的应用
- Lock锁的应用
- 原子类概述
- 堵塞队列以及线程安全集合等
- Executors和Services
- ForkJoin框架和并行流
- Actor模型
- disruptor并发框架
- jdk1.8多线程包的源码解读

----------


## 基本线程的应用 ##

## Lock锁的应用 ##

## 原子类概述 ##

##堵塞队列以及线程安全集合等##


## 线程池(Executors和ExecutorsService) ##  
1.ExecutorsService接口 继承 Executor接口  
  
	/**
	 * ExecutorService提供了管理终止和产生Future实例（用于跟踪异步任务进度）的方法。
	 * 
	 * Executor一旦终止，就没有正在执行的任务，也没有正在等待执行的任务，并且不能提交新的任务。 
	 * 应该关闭一个未使用的ExecutorService以允许资源的回收。
	 */
	public interface ExecutorService extends Executor {
	    /**
	     * 调用shutdown方法之后Executor会拒绝新的任务（task），
	     * 但允许在停止之前执行先前提交的任务
	     */
	    void shutdown();
	
	    /**
	     * 拒绝新的任务，并尝试停止正在执行和正在等待的任务，返回正在等待的任务列表
	     * 停止正在执行通常是通过Thread.interrupt()方法，这种情况无法终止不响应中断状态的线程
	     */
	    List<Runnable> shutdownNow();
	
	    //是否已经被shut down
	    boolean isShutdown();
	
	    //当executor被shut down，并且所有任务都被执行完，则返回ture，否则返回false
	    boolean isTerminated();
	
	    /**
	     * 阻塞直到下面任何一种情况发生
	     * 1.超过timeout指定的时间
	     * 2.调用shutdown，并且所有任务被执行完
	     * 3.当前线程被中断
	     */
	    boolean awaitTermination(long timeout, TimeUnit unit)
	        throws InterruptedException;
	
	    //提交Callable任务，并返回表示执行结果的Future实例
	    <T> Future<T> submit(Callable<T> task);
	
	    //提交Runnable任务，并返回表示任务的Future实例，通过该实例可以了解当前任务的执行状态
	    <T> Future<T> submit(Runnable task, T result);
	    Future<?> submit(Runnable task);
	
	    /**
	     * 执行给定的task，当所有task完成（执行完成、抛出异常）时，返回List<Future>
	     * 返回的每一个Future.isDone方法都返回true 
	     */
	    <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks)
	        throws InterruptedException;
	
	    /**
	     * 执行给定的task，当所有task完成（执行完成、抛出异常）或超时时，
	     * 返回List<Future>，返回的每一个Future.isDone方法都返回true
	     * 返回的时候，那些来不及完成的任务都会被取消
	     */
	    <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks,
	                                  long timeout, TimeUnit unit)
	        throws InterruptedException;
	
	    /**
	     * 执行给定的任务，当有一个任务成功执行完成（未抛出异常），则返回该任务的结果
	     * 如果所有任务都不能成功完成执行，则抛出ExecutionException异常
	     */
	    <T> T invokeAny(Collection<? extends Callable<T>> tasks)
	        throws InterruptedException, ExecutionException;
	
	    /**
	     * 执行给定的任务，当有一个任务成功执行完成（未抛出异常），则返回该任务的结果
	     * 如果超时还未有任务成功完成执行，则抛出TimeoutException异常
	     * 如果所有任务都不能成功完成执行，则抛出ExecutionException异常
	     */
	    <T> T invokeAny(Collection<? extends Callable<T>> tasks,
	                    long timeout, TimeUnit unit)
	        throws InterruptedException, ExecutionException, TimeoutException;
	}

AbstractExecutorService实现了ExecutorService接口作用的第2和第3点：  
submit()实现：创建FutureTask，并交给execute()方法执行 
invokeAny()实现：使用CompletionService提交任务，并获取结果，如果超时还未获取到结果，则抛出ExecutionException异常

AbstractExecutorService 通过调用CompletionService来实现具体的线程操作

2.Executors类可以看做一个“工具类”  

  		newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
		newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
		newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
		newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。

**线程池状态说明**

RUNNING：可以接受新任务，也可以处理阻塞队列里面的任务

SHUTDOWN：不接受新任务，但是可以处理阻塞队列里的任务

STOP：不在接收新任务，也不再处理阻塞队列里的任务，并中断正在处理的任务

TIDYING：中间状态：线程池中没有有效的线程，调用terminate进入TERMINATE状态

TERMINATE：终止状态

**线程池源码分析**

ExecutorService  executor = Executors.newFixedThreadPool(100);
通过API我们可以看到创建线程池的过程。

public static ExecutorService newFixedThreadPool(int nThreads) {
        return new ThreadPoolExecutor(nThreads, nThreads,
                                      0L, TimeUnit.MILLISECONDS,
                                      new LinkedBlockingQueue<Runnable>());
    }
Executors这个类中基本都是静态方法，代理了线程池的创建，大大简化了我么创建线程池工作量，通过方法名我们就可以创建我们想要的线程池，他的内部其实都是统一的方法实现的，通过构造方法重载实现不同的功能，但是不看源码，是很难知道他们的具体作用的。我们可以看到，这里面有好几种创建线程池的方法，他们有什么区别呢？

1. newFixedThreadPool(int)方法,内部实现如下：

		public static ExecutorService newFixedThreadPool(int nThreads) {
		        return new ThreadPoolExecutor(nThreads, nThreads,
		                                      0L, TimeUnit.MILLISECONDS,
		                                      new LinkedBlockingQueue<Runnable>()); 
		    }
创建指定大小的线程池，如果超出大小，放入block队列，即LinkedBlockingQueue队列，默认的线程工厂为defaultThreadFactory。ThreadPoolExecutor

2. newWorkStealingPool(int)，内部实现如下：

		public static ExecutorService newWorkStealingPool(int parallelism) {
		        return new ForkJoinPool
		            (parallelism,
		             ForkJoinPool.defaultForkJoinWorkerThreadFactory,
		             null, true);
		}
JDK1.8新增，返回ForkJoin，个人感觉有一点mapReduce的思想。

3.newSingleThreadPool，源码如下：
		
		public static ExecutorService newSingleThreadExecutor() {
		        return new FinalizableDelegatedExecutorService
		            (new ThreadPoolExecutor(1, 1,
		                                    0L, TimeUnit.MILLISECONDS,
		                                    new LinkedBlockingQueue<Runnable>()));
创建单个线程的线程池。ThreadPoolExecutor

4. newCachedThreadPool，源码如下：

		public static ExecutorService newCachedThreadPool() {
		        return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
		                                      60L, TimeUnit.SECONDS,
		                                      new SynchronousQueue<Runnable>());
		    }
线程池长度超过处理需要，灵活回收空闲线程，若无可回收，则创建新线程。 ThreadPoolExecutor 

5. newScheduledThreadPool，源码如下：

	    public static ScheduledExecutorService newScheduledThreadPool(
	            int corePoolSize, ThreadFactory threadFactory) {
	        return new ScheduledThreadPoolExecutor(corePoolSize, threadFactory);
	    }

	创建一个固定长度，可定时可延迟周期性执行的线程池，ScheduledThreadPoolExecutor继承ThreadPoolExecutor  

**如何设置线程的最大线程值？？**
首先我们很清楚，电脑的线程资源是有限的，正确来说cpu的资源是有限的，并不是线程越多进行速度就是越快的。
最合适的最大线程数该怎么确定，依赖以下两个方面：

- 任务的特征
- 计算机的硬件情况

首先一定要看的是cpu的核数，理论上线程最大数要至少大于等于cpu核数，因为有4个可用的CPU，意味着最多能够并行地执行4个任务。  
基线百分比 ：理想中的是25%。（当然，因为除了运行线程，还有一些后台线程占用cpu，所以基本不可能达到理想状态）  
基线百分比= N C的cpu下的运行时间/ 1 C的cpu下的运行时间。  

**如何设置线程的最小线程值？？**  
对于绝大部分场景，将它设置的和最大线程数相等就可以了。  
将最小线程数设置的小于最大线程数的初衷是为了节省资源，因为每多创建一个线程都会耗费一定量的资源，尤其是线程栈所需要的资源。但是在一个系统中，针对硬件资源以及任务特点选定了最大线程数之后，就表示这个系统总是会利用这些线程的，那么还不如在一开始就让线程池把需要的线程准备好。然而，把最小线程数设置的小于最大线程数所带来的影响也是非常小的，一般都不会察觉到有什么不同。

##ForkJoin框架和并行流##
**框架原理：**  
Fork/Join并行方式是获取良好的并行计算性能的一种最简单同时也是最有效的设计技术。Fork/Join并行算法是我们所熟悉的分治算法的并行版本，典型的用法如下：

		Result solve(Problem problem) {
		    if (problem is small) {
		        directly solve problem
		    } else {
		        split problem into independent parts
		        fork new subtasks to solve each part
		        join all subtasks
		        compose result from subresults
		    }
		}
fork操作将会启动一个新的并行Fork/Join子任务。join操作会一直等待直到所有的子任务都结束。Fork/Join算法，如同其他分治算法一样，总是会递归的、反复的划分子任务，直到这些子任务可以用足够简单的、短小的顺序方法来执行。

- 每一个工作线程维护自己的调度队列中的可运行任务。
- 队列以双端队列的形式被维护（注：deques通常读作『decks』），不仅支持后进先出 —— LIFO的push和pop操作，还支持先进先出 —— FIFO的take操作。
- 对于一个给定的工作线程来说，任务所产生的子任务将会被放入到工作者自己的双端队列中。
- 工作线程使用后进先出 —— LIFO（最新的元素优先）的顺序，通过弹出任务来处理队列中的任务。
- 当一个工作线程的本地没有任务去运行的时候，它将使用先进先出 —— FIFO的规则尝试随机的从别的工作线程中拿（『窃取』）一个任务去运行。
- 当一个工作线程触及了join操作，如果可能的话它将处理其他任务，直到目标任务被告知已经结束（通过isDone方法）。所有的任务都会无阻塞的完成。
- 当一个工作线程无法再从其他线程中获取任务和失败处理的时候，它就会退出（通过yield、sleep和/或者优先级调整，参考第3节）并经过一段时间之后再度尝试直到所有的工作线程都被告知他们都处于空闲的状态。在这种情况下，他们都会阻塞直到其他的任务再度被上层调用。  

*注意：当任务的任务量均衡时，选择ThreadPoolExecutor往往更好，反之则选择ForkJoinPool。*  
**总结**  

- 当需要处理递归分治算法时，考虑使用ForkJoinPool。
- 仔细设置不再进行任务划分的阈值，这个阈值对性能有影响。
- Java 8中的一些特性会使用到ForkJoinPool中的通用线程池。在某些场合下，需要调整该线程池的默认的线程数量。（parallelStream就是并行的流）  


**代码详解**

ForkJoinPool是ExecutorService的实现类，因此是一种特殊的线程池。ForkJoinPool提供了如下两个常用的构造器。  

-  public ForkJoinPool(int parallelism)：创建一个包含parallelism个并行线程的ForkJoinPool
-  public ForkJoinPool() ：以Runtime.getRuntime().availableProcessors()的返回值作为parallelism来创建ForkJoinPool
- 也可以通过Executors.newWorkStealingPool 线程池构造并调度 

**核心方法：**submit(ForkJoinTask<T> task)或者invoke(ForkJoinTask<T> task)来执行指定任务  
ForkJoinTask代表一个可以并行、合并的任务。ForkJoinTask是一个抽象类，它有两个抽象子类：RecursiveAction和RecursiveTask。   

- RecursiveTask代表有返回值的任务
- RecursiveAction代表没有返回值的任务。  

而我们要写的调度任务类，就需要继承实现RecursiveAction和RecursiveTask抽象子类。
