package com.tianque.command.one;

/**
 * Created by QQ on 2018/3/23.
 */
public class Entry2 implements Entry{
    @Override
    public void on() {
        System.out.println("Entry2 is on");
    }

    @Override
    public void off() {
        System.out.println("Entry2 is off");
    }
}
