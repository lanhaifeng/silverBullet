package com.tianque.pipelinepattern;

import lombok.extern.slf4j.Slf4j;

/**
 * @descripton:
 * @author: mr.0
 * @date: 2019-01-12 10:20
 */
@Slf4j
public class BasicValve extends AbstractValve {

    @Override
    public void invoke(String v) {
        v = v.concat("[basicvalve dealed]");
        log.error(v);
    }

}
