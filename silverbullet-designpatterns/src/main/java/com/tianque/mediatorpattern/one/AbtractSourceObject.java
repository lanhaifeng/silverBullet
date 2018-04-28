package com.tianque.mediatorpattern.one;

/**
 * Created by QQ on 2018/3/22.
 */
public abstract class AbtractSourceObject {
    protected String name;
    protected AbstractMediatorClass abstractMediatorClass;
    public AbtractSourceObject(String name,AbstractMediatorClass mediatorClass){
        this.name = name;
        this.abstractMediatorClass = mediatorClass;
    }
    protected void doSend(String message){
        abstractMediatorClass.doSend(message,this);
    }

    protected abstract void doRecept(String message);
}
