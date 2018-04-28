package com.tianque.proxypattern.three;

import org.junit.Test;
import org.springframework.cglib.proxy.Enhancer;

/**
 * Created by QQ on 2018/3/19.
 */
public class ProxyObjectTest {
    @Test
    public void testProxy(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(RealObject.class);
        enhancer.setCallback(new ProxyObject());
        RealObject realObject = (RealObject)enhancer.create();
        realObject.dosomething();
    }

}