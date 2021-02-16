package com.tomkate.java;

/**
 * 如何保证变量s指向的是字符串常量池中的数据呢？
 * 有两种方式
 * 方式一：String s = "tomkate"; //字面量定义的方式
 * 方式二：调用intern()方法
 * String s = new String("tomkate").intern();
 * String s = new StringBuilder("tomkate").toString().intern()
 *
 * @author Tom
 * @version 1.0
 * @date 2021/2/16 20:54
 */
public class StringIntern {
    public static void main(String[] args) {
        String s = new String("1");
        s.intern();
        String s2 = "1";
        System.out.println(s == s2);//false

        String s3 = new String("1") + new String("1");
        s3.intern();
        String s4 = "11";
        System.out.println(s3 == s4);//true
    }
}
