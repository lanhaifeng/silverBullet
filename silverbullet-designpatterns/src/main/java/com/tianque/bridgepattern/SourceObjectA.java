package com.tianque.bridgepattern;


import java.util.HashMap;

/**
 * 《桥接模式》
 * 代码基本接近装饰者模式，区别来说，概念上来说。装饰者类本身没有意义，他的存在就是为了被装饰者服务的，而桥接模式的两边都是独立的模块
 * 代码上来说，桥接模式没有中间类，是在一个模块上的抽象类上引用另一边的接口的方式，而装饰者是存在装饰者类的
 * @author  linlinan
 **/

public class SourceObjectA extends  SourceObject{
    @Override
    public void goToTarget() {
        targetObject.recept();
        HashMap hashMap = new HashMap();
        hashMap.entrySet();
    }
}
