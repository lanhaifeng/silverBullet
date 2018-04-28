package com.tianque.mediatorpattern.one;

/**
 * Created by QQ on 2018/3/22.
 */
public class SourceObject1 extends AbtractSourceObject{
    public SourceObject1(String name, AbstractMediatorClass mediatorClass) {
        super(name, mediatorClass);
    }

    @Override
    protected void doRecept(String message) {
        System.out.println("1 dorecept"+ message);
    }
}
