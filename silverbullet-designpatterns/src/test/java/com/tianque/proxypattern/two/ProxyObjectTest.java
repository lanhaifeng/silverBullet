package com.tianque.proxypattern.two;

import org.junit.Test;

import java.util.HashMap;

/**
 * 单例模式，线程安全
 * Created by QQ on 2018/3/15.
 */
public class ProxyObjectTest {
    @Test
    public void getInstance() throws Exception {
        Source source = (Source)new ProxyObject().getInstance(new RealObject());
        source.dosomething();
        return;
    }

    @Test
    public void invoke() throws Exception {
        System.out.println();
        HashMap map=  new HashMap(6,0.8f);
        for (int i = 0; i < 60; i++) {
            map.put("lin"+i,"linlinan");
        }
    }

}