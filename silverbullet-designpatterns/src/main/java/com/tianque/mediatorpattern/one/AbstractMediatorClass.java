package com.tianque.mediatorpattern.one;

/**
 * 《中介者模式》
 * 用一个中介对象来封装一系列的对象交互。中介者使各对象不需要显式地相互引用，从而使其耦合松散，而且可以独立地改变它们之间的交互。
 这个模式我们可以简单的类比于星型结构，中介者就是中心，其他对象的交互都是通过中心的中介者作为媒介，而不是直接交互，这样就避免了对象直接交互导致的关系混乱，不易维护的缺点。
 * 优点：
 在于简化了对象之间的交互，将各同事解耦，还可以减少子类生成，对于复杂的对象之间的交互，通过引入中介者，可以简化各同事类的设计和实现。
 * 缺点：
 在于具体中介者类中包含了同事之间的交互细节，可能会导致具体中介者类非常复杂，使得系统难以维护。
 * 使用场景：
 中介者模式适用于多个对象之间紧密耦合的情况，紧密耦合的标准是：在类图中出现了蜘蛛网状结构，即每个类都与其他的类有直接的联系。
 */
public abstract class AbstractMediatorClass {
    protected AbtractSourceObject a;
    protected AbtractSourceObject b;

    public AbtractSourceObject getA() {
        return a;
    }

    public void setA(AbtractSourceObject a) {
        this.a = a;
    }

    public AbtractSourceObject getB() {
        return b;
    }

    public void setB(AbtractSourceObject b) {
        this.b = b;
    }

    public abstract void doSend(String message,AbtractSourceObject sourceObjectA);
}
