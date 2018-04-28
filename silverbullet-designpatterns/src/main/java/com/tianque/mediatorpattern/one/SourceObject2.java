package com.tianque.mediatorpattern.one;

/**
 * Created by QQ on 2018/3/22.
 */
public class SourceObject2  extends AbtractSourceObject{
    public SourceObject2(String name, AbstractMediatorClass mediatorClass) {
        super(name, mediatorClass);
    }

    @Override
    protected void doRecept(String message) {
        System.out.println("2 dorecept" +message);
    }
}
