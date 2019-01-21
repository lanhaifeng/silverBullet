package com.tianque.pipelinepattern;

/**
 * @descripton:
 * @author: mr.0
 * @date: 2019-01-12 10:27
 */
public class Main {

    public static void main(String[] args) {
        StandardPipline pipline = new StandardPipline();
        pipline.setBasic(new BasicValve()).addValve(new TrimValve()).addValve(new WrapperValve());
        pipline.first().invoke("                        I am here !!!!! ");
    }

}
