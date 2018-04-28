package com.tianque.bridgepattern;

/**
 * Created by QQ on 2018/3/26.
 * @author  linlinan
 */
public abstract class SourceObject {
    TargetObject targetObject;

    public void setTargetObject(TargetObject targetObject) {
        this.targetObject = targetObject;
    }

    public abstract void goToTarget();
}
