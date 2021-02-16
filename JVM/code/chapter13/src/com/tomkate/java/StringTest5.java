package com.tomkate.java;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/2/16 19:59
 */
public class StringTest5 {
    public static void test1() {
        String s1 = "a" + "b" + "c";  //等同于"abc" 得到 abc的常量池
        String s2 = "abc"; // abc存放在常量池，直接将常量池的地址返回赋给s2
        /**
         * 最终java编译成.class，再执行.class
         */
        System.out.println(s1 == s2); // true，因为存放在字符串常量池
        System.out.println(s1.equals(s2)); // true
    }

    public static void test2() {
        String s1 = "javaEE";
        String s2 = "hadoop";
        String s3 = "javaEEhadoop";
        String s4 = "javaEE" + "hadoop"; //编译期优化

        //如果拼接符号前后出现了变量，则相当于在堆空间中new String(),具体内容为拼接的结果：javaEEhadoop
        String s5 = s1 + "hadoop";
        String s6 = "javaEE" + s2;
        String s7 = s1 + s2;

        System.out.println(s3 == s4); // true
        System.out.println(s3 == s5); // false 存在变量 结果放置于堆中
        System.out.println(s3 == s6); // false
        System.out.println(s3 == s7); // false
        System.out.println(s5 == s6); // false
        System.out.println(s5 == s7); // false
        System.out.println(s6 == s7); // false

        //intern():判断字符串常量池中是否存在javaEEhadoop，如果存在，则返回常量池中javaEEhadoop的地址，
        //如果字符串常量池中不存在javaEEhadoop，则在常量池中加载一份javaEEhadoop，并返回其对象的地址
        String s8 = s6.intern();
        System.out.println(s3 == s8); // true
    }

    public void test3() {
        String s1 = "a";
        String s2 = "b";
        String s3 = "ab";
        /**
         * 如下的s1+s2的执行细节（变量S是临时定义的）
         * StringBuilder s = new StringBuilder();
         * s.append("a");
         * s.append("b");
         * s.toString() ---> 约等于new String("ab")
         *
         * 补充JDK5.0之后使用的是StringBuilder
         * 在5.0之前使用的是StringBuffer
         */
        String s4 = s1 + s2;
        System.out.println(s3 == s4);//false
    }

    /**
     * 字符串拼接操作不一定使用的是StringBuilder！
     * 如果拼接符号左右两侧都是字符串常量或者引用，则仍然使用编译器优化，即非StringBuilder的方式
     * 针对于final修饰类、方法、基本数据类型、引用数据类型的量的结构时，能使用上final的时候建议使用上。
     */
    public void test4() {
        final String s1 = "a";
        final String s2 = "b";
        String s3 = "ab";
        String s4 = s1 + s2;
        System.out.println(s3 == s4);//true
    }

    public void test6() {
        long start = System.currentTimeMillis();
//        method1(100000);//4014
        method2(100000);//7
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start));
    }

    public void method1(int highLevel) {
        String src = "";
        for (int i = 0; i < highLevel; i++) {
            src = src + "a";//每次循环都会创建一个StringBuilder
        }
    }

    public void method2(int highLevel) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < highLevel; i++) {
            stringBuilder.append("a");
        }
    }
}
