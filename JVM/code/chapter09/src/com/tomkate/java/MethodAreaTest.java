package com.tomkate.java;

/**
 * non-final的类变量
 * @author Tom
 * @version 1.0
 * @date 2021/2/15 18:46
 */
public class MethodAreaTest {
    public static void main(String[] args) {
        Order order = new Order();
        order.hello();
        System.out.println(order.count);
    }
}

class Order {
    public static int count = 1;
    public static final int number = 2;

    public static void hello() {
        System.out.println("hello!");
    }
}
