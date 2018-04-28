package com.tianque.bridgepattern;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QQ on 2018/3/26.
 * @author  linlinan
 */
public class TargetObjectB implements TargetObject {
    @Override
    public void recept() {
        List list = new ArrayList();
        list.stream().filter(num -> num != null).count();
    }
}
