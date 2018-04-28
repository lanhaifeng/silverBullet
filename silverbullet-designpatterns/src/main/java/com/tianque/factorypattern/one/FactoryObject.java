package com.tianque.factorypattern.one;

/**
 * 简单工厂模式，静态工厂模式。个人理解工厂模式跟代理模式的最大区别，工厂模式在于创建对象，代理模式在于扩展对象
 */
public class FactoryObject {
    public static final int TYPE_ONE = 1;//兰州拉面
    public static final int TYPE_TWO = 2;//泡面
    public static final int TYPE_THREE = 3;//干扣面

    public static AbstractObject getRealObject(int type){
        switch (type) {
            case TYPE_ONE:
                return new RealObjectOne();
            case TYPE_TWO:
                return new RealObjectTwo();
            case TYPE_THREE:
                return new RealObjectThree();
            default:
                return null;
        }
    }
}
