package com.tomkate.java;

/**
 * 测试System.gc()
 *
 * @author Tom
 * @version 1.0
 * @date 2021/2/18 15:08
 */
public class SystemGCTest {
    public static void main(String[] args) {
        new SystemGCTest();
        // 提醒JVM进行垃圾回收,但是不确定是否立马执行
        //与Runtime.getRuntime().gc()的作用一样
        System.gc();
        //强制调用失去引用的对象的finallize()方法
        System.runFinalization();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("SystemGCTest 执行了 finalize方法");
    }
}
