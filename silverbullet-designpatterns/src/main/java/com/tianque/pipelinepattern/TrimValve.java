package com.tianque.pipelinepattern;

import org.springframework.util.StringUtils;

/**
 * @descripton:
 * @author: mr.0
 * @date: 2019-01-12 10:24
 */
public class TrimValve extends AbstractValve {

    @Override
    public void invoke(String v) {
        v = StringUtils.trimWhitespace(v);
        getNext().invoke(v);
    }

}
