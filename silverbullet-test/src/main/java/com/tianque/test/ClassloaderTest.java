package com.tianque.test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * @descripton:
 * @author: mr.0
 * @date: 2019-01-15 11:17
 */
public class ClassloaderTest {
    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException {

        URL url= new File("D:\\workspace\\maven_repo\\cn\\hutool\\hutool-all\\4.1.4\\hutool-all-4.1.4.jar").toURI().toURL();//将File类型转为URL类型，file为jar包路径
        URLClassLoader urlClassLoader = AccessController.doPrivileged(new PrivilegedAction<URLClassLoader>() {
            @Override
            public URLClassLoader run() {
                return new URLClassLoader(new URL[]{url});
            }
        });

        Class c=urlClassLoader.loadClass("cn.hutool.core.util.StrUtil");
        System.out.println(c);
//        Class c=Class.forName("cn.hutool.core.util.StrUtil");
    }
}
