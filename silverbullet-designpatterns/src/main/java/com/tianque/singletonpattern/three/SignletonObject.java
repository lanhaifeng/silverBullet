package com.tianque.singletonpattern.three;
/**
 * 《单例模式》 懒汉式
 * 优点：
 提供了对唯一实例的受控访问；
 由于加载一个类时，其内部类不会被加载。这样保证了只有调用getInstance()时才会产生实例，控制了生成实例的时间，实现了延迟加载。
 并且去掉了synchronized，让性能更优，用static来确保唯一性。
 */
public class SignletonObject {
        private SignletonObject(){
            System.out.println("StaticSingleton is create");
        }
        private static class SingletonHolder {
            private static SignletonObject instance = new SignletonObject();
        }
        public static SignletonObject getInstance() {
            return SingletonHolder.instance;
        }
}
