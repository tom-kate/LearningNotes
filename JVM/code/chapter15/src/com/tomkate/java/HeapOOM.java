package com.tomkate.java;

import java.util.ArrayList;

/**
 * 内存溢出排查
 * -Xms8m -Xmx8m -XX:+HeapDumpOnOutOfMemoryError
 *
 * @author Tom
 * @version 1.0
 * @date 2021/2/18 11:42
 */
public class HeapOOM {
    // 创建1M的文件
    byte[] buffer = new byte[1 * 1024 * 1024];

    public static void main(String[] args) {
        ArrayList<HeapOOM> list = new ArrayList<>();
        int count = 0;
        try {
            while (true) {
                list.add(new HeapOOM());
                count++;
            }
        } catch (Exception e) {
            System.out.println("count:" + count);
            e.getStackTrace();
        }
    }
}
