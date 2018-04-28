package com.tianque.jdk8;

/**
 *@author:linlinan
 *@version:1.0
 *@Date:20180408
 * java 8 新增了接口的默认方法。
简单说，默认方法就是接口可以有实现方法，而且不需要实现类去实现其方法。
静态默认方法
Java 8 的另一个特性是接口可以声明（并且可以提供实现）静态方法。例如：
 **/
public interface Two {
    default void test(){
        System.out.println("123");
    }
    static void test1(){

    }
}
