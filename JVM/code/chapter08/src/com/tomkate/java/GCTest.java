package com.tomkate.java;

import java.util.ArrayList;

/**
 * 测试 Minor GC、Major GC、Full GC
 *
 * @author Tom
 * @version 1.0
 * @date 2021/2/11 12:21
 */
public class GCTest {
    public static void main(String[] args) {
        int i = 0;
        ArrayList<String> list = new ArrayList<>();
        String str = "com.tomkate";
        try {
            while (true) {
                list.add(str);
                str = str + str;
                ++i;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("执行次数为：" + i);
        }

    }
}
