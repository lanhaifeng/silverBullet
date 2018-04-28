package com.tianque.proxypattern.three;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 动态代理的实现，cglib动态代理
 * Created by QQ on 2018/3/15.
 */
public class ProxyObject implements MethodInterceptor{
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("before "+method.getName());
        Object object = methodProxy.invokeSuper(o,objects);
        System.out.println("after "+method.getName());
        return o;
    }
}
