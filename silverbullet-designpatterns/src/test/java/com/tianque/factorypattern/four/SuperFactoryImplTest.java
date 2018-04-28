package com.tianque.factorypattern.four;

import com.tianque.factorypattern.three.AbstractFactory;
import org.junit.Test;

/**
 * Created by QQ on 2018/3/20.
 */
public class SuperFactoryImplTest {
    @Test
    public void testFactory(){
        SuperFactory superFactory = new SuperFactoryImpl();
        AbstractFactory abstractFactory = superFactory.getFactoryOne();
        abstractFactory.create().doSomething();
    }
}