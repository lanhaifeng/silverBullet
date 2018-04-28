package com.tianque.jdk8;

import org.apache.commons.lang3.StringUtils;

/**
 *@author:linlinan
 *@version:1.0
 *@Date:20180408
 * 1.Java 8 Lambda 表达式
Java 8 新特性 Java 8 新特性
Lambda 表达式，也可称为闭包，它是推动 Java 8 发布的最重要新特性。
Lambda 允许把函数作为一个方法的参数（函数作为参数传递进方法中）。
使用 Lambda 表达式可以使代码变的更加简洁紧凑。

lambda 表达式的局部变量可以不用声明为 final，但是必须不可被后面的代码修改（即隐性的具有 final 的语义）
在 Lambda 表达式当中不允许声明一个与局部变量同名的参数或者局部变量。
 2.方法的引用::
构造器引用：它的语法是Class::new，或者更一般的Class< T >::new
静态方法引用：它的语法是Class::static_method
特定类的任意对象的方法引用：它的语法是Class::method
特定对象的方法引用：它的语法是instance::method
 **/
public class One {
    public static void main(String[] args) {
        FunctionalInterfaceOne functionalInterfaceOne = (String a) -> {System.out.println(a);};
        One one = new One();
        one.operate("123123123",One::operateOne);
    }
    //新名词——函数式接口1（Functional Interface）
    //SAM类型接口（single abstract interface）
    @FunctionalInterface
    interface FunctionalInterfaceOne{
        void test1(String message);
    }

    public static String operateOne (String a){
        return a = StringUtils.isNotEmpty(a)?"true":"false";
    }
    /*    将方法声明为final那有两个原因，第一就是说明你已经知道这个方法提供的功能已经满足你要求，不需要进行扩展，并且也不允许任何从此类继承的类来覆写这个方法，但是继承仍然可以继承这个方法，
    也就是说可以直接使用。第二就是允许编译器将所有对此方法的调用转化为inline调用的机制，它会使你在调用final方法时，直接将方法主体插入到调用处，而不是进行例行的方法调用，例如保存断点，压栈等，这样可能会使你的程序效率有所提高，然而当你的方法主体非常庞大时，或你在多处调用此方法，那么你的调用主体代码便会迅速膨胀，可能反而会影响效率，所以你要慎用final进行方法定义。
    */
    public final void operate(String a,FunctionalInterfaceOne functionalInterface){
        functionalInterface.test1(a);
    }
}
