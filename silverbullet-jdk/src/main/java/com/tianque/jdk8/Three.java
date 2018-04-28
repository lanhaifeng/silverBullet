package com.tianque.jdk8;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *@author:linlinan
 *@version:1.0
 *@Date:20180408
 * java 8 新增了API添加了一个新的抽象称为流Stream，可以让你以一种声明的方式处理数据。。
Stream（流）是一个来自数据源的元素队列并支持聚合操作
元素是特定类型的对象，形成一个队列。 Java中的Stream并不会存储元素，而是按需计算。
数据源 流的来源。 可以是集合，数组，I/O channel， 产生器generator 等。
聚合操作 类似SQL语句一样的操作， 比如filter, map, reduce, find, match, sorted等。
和以前的Collection操作不同， Stream操作还有两个基础的特征：
Pipelining: 中间操作都会返回流对象本身。 这样多个操作可以串联成一个管道， 如同流式风格（fluent style）。 这样做可以对操作进行优化， 比如延迟执行(laziness)和短路( short-circuiting)。
内部迭代： 以前对集合遍历都是通过Iterator或者For-Each的方式, 显式的在集合外部进行迭代， 这叫做外部迭代。 Stream提供了内部迭代的方式， 通过访问者模式(Visitor)实现。

stream or parallelStream

parallelStream所带来的隐患和好处,那么,在从stream和parallelStream方法中进行选择时,我们可以考虑以下几个问题：
1. 是否需要并行？
2. 任务之间是否是独立的？是否会引起任何竞态条件？
3. 结果是否取决于任务的调用顺序？
对于问题1，在回答这个问题之前，你需要弄清楚你要解决的问题是什么，数据量有多大，计算的特点是什么？并不是所有的问题都适合使用并发程序来求解，比如当数据量不大时，顺序执行往往比并行执行更快。毕竟，准备线程池和其它相关资源也是需要时间的。但是，当任务涉及到I/O操作并且任务之间不互相依赖时，那么并行化就是一个不错的选择。通常而言，将这类程序并行化之后，执行速度会提升好几个等级。

对于问题2，如果任务之间是独立的，并且代码中不涉及到对同一个对象的某个状态或者某个变量的更新操作，那么就表明代码是可以被并行化的。

对于问题3，由于在并行环境中任务的执行顺序是不确定的，因此对于依赖于顺序的任务而言，并行化也许不能给出正确的结果。
 **/
public class Three {
    public static void main(String[] args) {
        List<Integer> widgets = new ArrayList<>();
        widgets.add(1);
        widgets.add(2);
        widgets.add(3);
        widgets.add(4);
        widgets.add(5);
        List<Integer> transactionsIds =
                widgets.stream()
                        .filter(b -> b>1).collect(Collectors.toList());
    }
}
