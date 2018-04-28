package com.tianque.command.one;

/**
 * Created by QQ on 2018/3/23.
 */
public class OneOnCommand implements Command{
    Entry1 entry1;

    public Entry1 getEntry1() {
        return entry1;
    }

    public void setEntry1(Entry1 entry1) {
        this.entry1 = entry1;
    }

    @Override
    public void excute() {
        entry1.on();
    }

}
