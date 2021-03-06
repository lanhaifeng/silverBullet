package com.tianque.jdk8;

import java.util.Optional;

/**
 *@author:linlinan
 *@version:1.0
 *@Date:20180408
 * Java 8 Optional 类
Optional 类是一个可以为null的容器对象。如果值存在则isPresent()方法会返回true，调用get()方法会返回该对象。
Optional 是个容器：它可以保存类型T的值，或者仅仅保存null。Optional提供很多有用的方法，这样我们就不用显式进行空值检测。
Optional 类的引入很好的解决空指针异常。
 **/
public class Four
{
    class TestDo{
        String s1;
        String s2;
        String s3;
        public TestDo(String s1,String s2,String s3){
            this.s1 = s1;
            this.s2 = s2;
            this.s3 = s3;
        }

        @Override
        public String toString() {
            return s1+s2+s3;
        }
    }
    public static void main(String[] args) {
        Four four = new Four();
        Four.TestDo testDo = four.new TestDo("s1","s2","s3");

        Optional<Four.TestDo> integerOptional = Optional.ofNullable(testDo);
        System.out.println(integerOptional.get());
        integerOptional.get();
    }
}
