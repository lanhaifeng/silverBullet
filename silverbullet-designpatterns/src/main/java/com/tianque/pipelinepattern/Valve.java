package com.tianque.pipelinepattern;

/**
 * @descripton:
 * @author: mr.0
 * @date: 2019-01-12 10:13
 */
public interface Valve {

    void setNext(Valve next);

    Valve getNext();

    void invoke(String v);

}
