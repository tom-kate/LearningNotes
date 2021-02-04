package com.tomkate.java;


/**
 * @author Tom
 * @version 1.0
 * @date 2021/1/21 19:17
 */
public class ClassLoaderTest01 {
    public static void main(String[] args) {

        //获取系统类加载器
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println(systemClassLoader);
        //sun.misc.Launcher$AppClassLoader@18b4aac2

        //获取器上层：扩展类加载器
        ClassLoader extClassLoader = systemClassLoader.getParent();
        System.out.println(extClassLoader);
        //sun.misc.Launcher$ExtClassLoader@1b6d3586

        //获取器上层：获取不到引导类加载器
        ClassLoader bootstrapClassLoader = extClassLoader.getParent();
        System.out.println(bootstrapClassLoader);
        // null

        //对于用户自定义类来说：默认使用系统类加载器加载
        ClassLoader classLoader = ClassLoaderTest01.class.getClassLoader();
        System.out.println(classLoader);
        //sun.misc.Launcher$AppClassLoader@18b4aac2

        //Stirng类使用引导类加载器进行加载的 ----> Java的核心类库都是使用引导类加载器加载的
        ClassLoader classLoader1 = String.class.getClassLoader();
        System.out.println(classLoader1);
        // null
    }
}
