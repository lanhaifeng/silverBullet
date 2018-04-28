package com.tianque.facadepattern.one;

/**
 * 《外观模式》
 * 意图：为子系统中的一组接口提供一个一致的界面，外观模式定义了一个高层接口，这个接口使得这一子系统更加容易使用。
 主要解决：降低访问复杂系统的内部子系统时的复杂度，简化客户端与之的接口。
 优点： 1、减少系统相互依赖。 2、提高灵活性。 3、提高了安全性。
 缺点：不符合开闭原则，如果要改东西很麻烦，继承重写都不合适。
 使用场景： 1、为复杂的模块或子系统提供外界访问的模块。 2、子系统相对独立。 3、预防低水平人员带来的风险。
 */
public class FacadeObject {
    RealObjectOne realObjectOne;
    RealObjectTwo realObjectTwo;
    RealObjectThree realObjectThree;
    public FacadeObject( RealObjectOne realObjectOne,RealObjectTwo realObjectTwo,RealObjectThree realObjectThree){
        this.realObjectOne = realObjectOne;
        this.realObjectTwo = realObjectTwo;
        this.realObjectThree = realObjectThree;
    }

    public void doOne(){
        realObjectOne.doSomething();
    }

    public void doTwo(){
        realObjectTwo.doSomething();
    }

    public void doThree(){
        realObjectThree.doSomething();
    }

}
