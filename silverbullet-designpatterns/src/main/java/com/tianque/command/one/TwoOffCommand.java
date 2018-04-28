package com.tianque.command.one;

/**
 * Created by QQ on 2018/3/23.
 */
public class TwoOffCommand implements Command{
    Entry2 entry2;

    public Entry2 getEntry2() {
        return entry2;
    }

    public void setEntry2(Entry2 entry2) {
        this.entry2 = entry2;
    }

    @Override
    public void excute() {
        entry2.off();
    }
}
