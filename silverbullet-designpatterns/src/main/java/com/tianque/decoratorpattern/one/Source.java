package com.tianque.decoratorpattern.one;


/**
 * 《装饰者模式》
 * 动态地给一个对象添加一些额外的职责。就增加功能来说，装饰模式相比生成子类更为灵活。
 * 使用场景：
 ● 需要扩展一个类的功能，或给一个类增加附加功能。
 ● 需要动态地给一个对象增加功能，这些功能可以再动态地撤销。
 ● 需要为一批的兄弟类进行改装或加装功能，当然是首选装饰模式。
 */
public interface Source {
    void dosomething();
}
