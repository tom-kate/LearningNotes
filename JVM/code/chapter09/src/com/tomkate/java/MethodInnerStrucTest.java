package com.tomkate.java;

import java.io.Serializable;

/**
 * 测试方法区内部结构
 * @author Tom
 * @version 1.0
 * @date 2021/2/15 18:26
 */
public class MethodInnerStrucTest extends Object implements Comparable<String>, Serializable {

    @Override
    public int compareTo(String o) {
        return 0;
    }

    //属性
    public int num = 10;
    private static String str = "方法区内部结构";

    //构造器

    //方法
    public void test1() {
        int count = 20;
        System.out.println("count = " + count);
    }

    public static int test2(int cal) {
        int result = 0;
        try {
            int value = 30;
            result = value / cal;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
