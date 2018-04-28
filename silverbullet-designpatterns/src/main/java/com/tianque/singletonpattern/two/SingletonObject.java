package com.tianque.singletonpattern.two;

/**
 * 《单例模式》 懒汉式
 * 优点：
 提供了对唯一实例的受控访问；
 由于在系统内存中只存在一个对象，因此可以节约系统资源，对于一些需要频繁创建和销毁的对象单例模式无疑可以提高系统的性能；
 可以根据实际情况需要，在单例模式的基础上扩展做出双例模式，多例模式；
 * 缺点：
 单例类的职责过重，里面的代码可能会过于复杂，在一定程度上违背了“单一职责原则”。
 如果实例化的对象长时间不被利用，会被系统认为是垃圾而被回收，这将导致对象状态的丢失。
 * 应用场景：
 除了初始化单例类时 即 创建单例外，继续延伸出来的是：单例对象 要求初始化速度快 & 占用内存小
 */
public class SingletonObject {
    private static SingletonObject singleton = null;
    //限制产生多个对象
    private SingletonObject(){
    }
    //通过该方法获得实例对象，方法加上synchronized即可实现线程安全
    public synchronized static SingletonObject getSingleton(){
        if(singleton == null){
            singleton = new SingletonObject();
        }
        return singleton;
    }
}
