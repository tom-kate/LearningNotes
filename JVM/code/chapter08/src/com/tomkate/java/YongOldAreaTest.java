package com.tomkate.java;

/**
 * 测试大对象直接进入老年代
 * <p>
 * -Xms60m -Xmx60m -XX:NewRatio=2 -XX:SurvivorRatio=8 -XX:+PrintGCDetails
 *
 * @author Tom
 * @version 1.0
 * @date 2021/2/11 14:49
 */
public class YongOldAreaTest {
    public static void main(String[] args) {
        byte[] bytes = new byte[1024 * 1024 * 20];
    }
}
