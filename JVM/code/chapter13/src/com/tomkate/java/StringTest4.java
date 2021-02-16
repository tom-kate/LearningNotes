package com.tomkate.java;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/2/16 19:48
 */
public class StringTest4 {
    public static void main(String[] args) {
        System.out.println();//2118
        System.out.println("1");//2219
        System.out.println("2");//2120
        System.out.println("3");
        System.out.println("4");
        System.out.println("5");
        System.out.println("6");
        System.out.println("7");
        System.out.println("8");
        System.out.println("9");
        System.out.println("10");//2128

        //如下的字符串"1"到"10" 不会再次增加
        System.out.println("1");//2129
        System.out.println("2");//2129
        System.out.println("3");
        System.out.println("4");
        System.out.println("5");
        System.out.println("6");
        System.out.println("7");
        System.out.println("8");
        System.out.println("9");
        System.out.println("10");//2129
    }
}
