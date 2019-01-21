package com.tianque.pipelinepattern;

/**
 * @descripton:
 * @author: mr.0
 * @date: 2019-01-12 10:13
 */
public interface Pipeline {

    Valve first();

    Valve basic();

    StandardPipline addValve(Valve valve);

    StandardPipline setBasic(Valve valve);

}
