package com.tianque.command.one;

/**
 * Created by QQ on 2018/3/23.
 */
public class Entry1 implements Entry{
    @Override
    public void on() {
        System.out.println("Entry1 is on");
    }

    @Override
    public void off() {
        System.out.println("Entry1 is off");
    }
}
