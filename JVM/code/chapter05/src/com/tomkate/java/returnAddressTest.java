package com.tomkate.java;

import java.util.Date;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/2/5 16:24
 */
public class returnAddressTest {
    /**
     * 方法返回地址
     *
     * @param args
     */
    public static void main(String[] args) {

    }

    public byte returnByte() {
        return 0;
    }

    public char returnChar() {
        return 0;
    }

    public short returnShort() {
        return 0;
    }

    public int returnInt() {
        return 0;
    }

    public boolean returnBoolean() {
        return false;
    }

    public Long returnLong() {
        return 0L;
    }

    public float returnFloat() {
        return 0.0f;
    }

    public double returnDouble() {
        return 0.0;
    }

    public String returnString() {
        return "A";
    }

    public Date returnDate() {
        return new Date();
    }

    public void returnVoid() {

    }

    /**
     * 异常处理表
     */
    public void test1() {
        try {
            test2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void test2() throws Exception {
        int i = 10 / 0;
    }
}
