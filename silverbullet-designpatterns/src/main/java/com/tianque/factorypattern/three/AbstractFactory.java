package com.tianque.factorypattern.three;

import com.tianque.factorypattern.one.AbstractObject;

/**
 * 工厂方法模式，有点傻~~好处是需求有修改的时候只要增加工厂类就行，坏处是，代码多写~
 */
public abstract class AbstractFactory {
    public abstract AbstractObject create();
}
