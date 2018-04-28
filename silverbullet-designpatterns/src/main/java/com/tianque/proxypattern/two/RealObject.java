package com.tianque.proxypattern.two;

/**
 * 动态代理的实现，jdk动态代理
 * Created by QQ on 2018/3/15.
 */
public class RealObject implements Source{
    @Override
    public void dosomething() {
        System.out.println("1234");
    }
}
