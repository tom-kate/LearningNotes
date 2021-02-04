package com.tomkate.java;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/1/22 20:17
 */
public class StackErrorTest01 {
    //模拟 StackOverflowError 堆栈溢出错误
//    public static void main(String[] args) {
//        main(args);
//    }

    /**
     * 模拟设置栈大小
     *
     * 默认情况下：count：11414
     * 设置栈大小： -Xss256k：count：2477
     */
    private static int  a = 1;
    public static void main(String[] args) {
        System.out.println(a);
        a++;
        main(args);
    }
}
