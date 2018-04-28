package com.tianque.factorypattern.four;

import com.tianque.factorypattern.three.AbstractFactory;

/**
 * 抽象工厂模式，在工厂方法模式基础上，为工厂类提供工厂类
 */
public interface  SuperFactory {
    public AbstractFactory getFactoryOne();
    public AbstractFactory getFactoryTwo();
    public AbstractFactory getFactoryThree();
}
