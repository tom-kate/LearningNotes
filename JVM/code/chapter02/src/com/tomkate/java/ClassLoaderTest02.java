package com.tomkate.java;

import com.sun.net.ssl.internal.ssl.Provider;
import sun.misc.Launcher;
import sun.security.ec.CurveDB;

import java.net.URL;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/1/21 18:52
 */
public class ClassLoaderTest02 {
    public static void main(String[] args) {
        System.out.println("******************启动类加载器******************");
        //获取BootstrapClassLoader能够加载的api的路径
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        for (URL urL : urLs) {
            System.out.println(urL.toExternalForm());
        }
        //从上面的路径中随意挑选一个类，来看看它的类加载器是什么:引导类加载器
        //file:/D:/Program%20Files/Java/jdk1.8.0_191/jre/lib/jsse.jar
        ClassLoader classLoader = Provider.class.getClassLoader();
        System.out.println(classLoader);
        //结果为null即为引导类加载器

        System.out.println("******************扩展类加载器******************");
        String property = System.getProperty("java.ext.dirs");
        System.out.println(property);
        for (String path:property.split(";")){
            System.out.println(path);
        }
        //从上面的路径中随意挑选一个类，来看看它的类加载器是什么:扩展类加载器
        ClassLoader classLoader1 = CurveDB.class.getClassLoader();
        System.out.println(classLoader1);
        //sun.misc.Launcher$ExtClassLoader@4b67cf4d
    }
}
