package com.tianque.pipelinepattern;

/**
 * @descripton:
 * @author: mr.0
 * @date: 2019-01-12 10:21
 */
public abstract class AbstractValve implements Valve{

    Valve next;

    @Override
    public void setNext(Valve next) {
        this.next = next;
    }

    @Override
    public Valve getNext() {
        return next;
    }

}
