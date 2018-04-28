package com.tianque.factorypattern.four;

import com.tianque.factorypattern.three.AbstractFactory;
import com.tianque.factorypattern.three.RealObjectOneFactory;
import com.tianque.factorypattern.three.RealObjectThreeFactory;
import com.tianque.factorypattern.three.RealObjectTwoFactory;

/**
 * Created by QQ on 2018/3/20.
 */
public class SuperFactoryImpl implements SuperFactory{
    @Override
    public AbstractFactory getFactoryOne() {
        return new RealObjectOneFactory();
    }

    @Override
    public AbstractFactory getFactoryTwo() {
        return new RealObjectTwoFactory();
    }

    @Override
    public AbstractFactory getFactoryThree() {
        return new RealObjectThreeFactory();
    }
}
