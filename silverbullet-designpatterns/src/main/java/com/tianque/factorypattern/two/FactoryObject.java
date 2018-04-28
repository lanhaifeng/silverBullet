package com.tianque.factorypattern.two;

import com.tianque.factorypattern.one.AbstractObject;
import com.tianque.factorypattern.one.RealObjectOne;
import com.tianque.factorypattern.one.RealObjectThree;
import com.tianque.factorypattern.one.RealObjectTwo;

/**
 * 多方法工厂模式,个人觉得有点傻，每多一个类就要在工厂里面多加一种方法
 */
public class FactoryObject{

    public AbstractObject getOne(){
        return new RealObjectOne();
    }

    public AbstractObject getTwo(){
        return new RealObjectTwo();
    }

    public AbstractObject getThree(){
        return new RealObjectThree();
    }
}
