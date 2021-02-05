package com.tomkate.java;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/2/5 12:40
 */
public class OperandStackTest {
    public static void main(String[] args) {

    }

    public void testAddOperation() {
        byte i = 15;
        int j = 8;
        int k = i + j;
    }

    public int getNum() {
        int a = 10;
        int b = 20;
        int c = a + b;
        return c;
    }

    public void testGetNum() {
        //获取上一栈帧返回结果，并保存在操作数栈中
        int num = getNum();
        int k = 10;
    }
}
