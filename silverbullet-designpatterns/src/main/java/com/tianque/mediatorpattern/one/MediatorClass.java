package com.tianque.mediatorpattern.one;

/**
 * Created by QQ on 2018/3/22.
 */
public class MediatorClass extends AbstractMediatorClass{
    @Override
    public void doSend(String message, AbtractSourceObject sourceObjectA) {
        if(a==sourceObjectA){
            b.doRecept(message);
        }
        if(b==sourceObjectA){
            a.doRecept(message);
        }

    }
}
