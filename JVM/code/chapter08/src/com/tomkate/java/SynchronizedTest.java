package com.tomkate.java;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/2/15 15:52
 */
public class SynchronizedTest {
    public void f() {
        Object hellis = new Object();
        synchronized (hellis) {
            System.out.println(hellis);
        }
    }
}
