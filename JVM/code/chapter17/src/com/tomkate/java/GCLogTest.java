package com.tomkate.java;

/**
 * GC垃圾回收过程
 * <p>
 * -Xms60m -Xmx60m -XX:SurvivorRatio=8 -XX:+PrintGCDetails -Xloggc:./logs/gc.log -XX:+UseSerialGC
 *
 * @author Tom
 * @version 1.0
 * @date 2021/2/20 22:04
 */
public class GCLogTest {
    static final Integer _1MB = 1024 * 1024;

    public static void main(String[] args) {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB];
    }
}
