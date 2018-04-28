package com.tianque.proxypattern.one;

/**
 * 《代理者模式》
 * 为其他对象提供一种代理以控制对这个对象的访问。
 * 普通代理和强制代理：
 普通代理就是我们要知道代理的存在，也就是类似的GamePlayerProxy这个类的存在，然后才能访问；
 强制代理则是调用者直接调用真实角色，而不用关心代理是否存在，其代理的产生是由真实角色决定的。
 */
public class ProxyObject implements Source {
    Source source = new RealObject();
    @Override
    public void dosomething() {
        //doBefore
        source.dosomething();
        //doAfter
    }
}
