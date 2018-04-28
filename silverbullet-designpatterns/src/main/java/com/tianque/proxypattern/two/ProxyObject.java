package com.tianque.proxypattern.two;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/**
 * 动态代理的实现，jdk动态代理
 * Created by QQ on 2018/3/15.
 */
public class ProxyObject implements InvocationHandler {
    private Source source;

    public Object getInstance(Source target){
        this.source = target;
        Class clazz = target.getClass();
        Object obj = Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
        return obj;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Inside the invocation handler");
        try {
            return method.invoke(source, args);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

}
