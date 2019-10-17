package com.tianque.pipelinepattern;

/**
 * @descripton:
 * @author: mr.0
 * @date: 2019-01-12 10:48
 */
public class WrapperValve extends BasicValve{

    @Override
    public void invoke(String v) {
        v = "【".concat(v).concat("】");
        getNext().invoke(v);
    }

}
