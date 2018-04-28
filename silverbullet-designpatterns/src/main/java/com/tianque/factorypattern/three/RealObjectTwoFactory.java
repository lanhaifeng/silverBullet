package com.tianque.factorypattern.three;

import com.tianque.factorypattern.one.AbstractObject;
import com.tianque.factorypattern.one.RealObjectTwo;

/**
 * Created by QQ on 2018/3/20.
 */
public class RealObjectTwoFactory extends AbstractFactory{
    @Override
    public AbstractObject create() {
        return new RealObjectTwo();
    }
}
