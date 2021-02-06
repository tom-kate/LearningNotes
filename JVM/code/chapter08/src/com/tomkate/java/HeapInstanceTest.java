package com.tomkate.java;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/2/6 17:21
 */
public class HeapInstanceTest {
    byte [] buffer = new byte[new Random().nextInt(1024 * 200)];
    public static void main(String[] args) throws InterruptedException {
        ArrayList<HeapInstanceTest> list = new ArrayList<>();
        while (true) {
            list.add(new HeapInstanceTest());
            Thread.sleep(10);
        }
    }
}
