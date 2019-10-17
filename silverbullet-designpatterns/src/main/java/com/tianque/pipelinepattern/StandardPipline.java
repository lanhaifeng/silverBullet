package com.tianque.pipelinepattern;

import lombok.extern.slf4j.Slf4j;

/**
 * @descripton:
 * @author: mr.0
 * @date: 2019-01-12 10:18
 */
@Slf4j
public class StandardPipline implements Pipeline{

    private Valve first;

    private Valve basic;

    @Override
    public Valve first() {
        return first;
    }

    @Override
    public Valve basic() {
        return basic;
    }

    @Override
    public StandardPipline addValve(Valve valve) {
        if(valve == null){
            log.error("the pipline can not set null valve");
            return this;
        }
        if(basic == null){
            log.error("the pipline need to be setted basicvalve first");
            return this;
        }
        if(this.first == null){
            this.first = valve;
            this.first.setNext(basic);
        }else{
            //后进先出
//            Valve current = first;
//            first = valve;
//            valve.setNext(current);
            //先进先出
            first.setNext(valve);
            valve.setNext(basic);
        }
        return this;
    }

    @Override
    public StandardPipline setBasic(Valve basic) {
        this.basic = basic;
        return this;
    }

}
