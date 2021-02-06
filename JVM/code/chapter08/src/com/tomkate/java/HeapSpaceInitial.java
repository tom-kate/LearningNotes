package com.tomkate.java;

/**
 * 1.设置堆空间大小的参数
 * -Xms 用来设置堆空间（年轻代+老年代）的初始内存大小
 *      -X 是jvm的运行参数
 *      ms 是memory start
 *-Xmx 用来设置堆空间（年轻代+老年代）的最大内存大小
 *
 * 2.默认堆空间的大小
 *      初始内存大小：物理电脑内存大小/64
 *      最大内存大小：物理电脑内存大小/4
 *
 * 3.自定义设置堆内存：-Xms600m -Xmx600m
 *      开发中建议将初始内存和最大的堆内存设置为相同值
 *
 * 4.如何查看设置参数
 *      方式一：jps / jstat -gc 进程id
 *      方式二：-XX:+PrintGCDetails
 * @author Tom
 * @version 1.0
 * @date 2021/2/6 14:04
 */
public class HeapSpaceInitial {
    public static void main(String[] args) {
        //返回java虚拟机中的堆内存总量
        long initialMemory = Runtime.getRuntime().totalMemory() / 1024 / 1024;
        //返回java虚拟机试图使用的最大堆内存
        long maxMemory = Runtime.getRuntime().maxMemory() / 1024 / 1024;

        System.out.println("-Xms : " + initialMemory + "M");
        System.out.println("-Xmx : " + maxMemory + "M");

        System.out.println("系统实际内存大小为：" + initialMemory * 64.0 / 1024 + "G");
        System.out.println("JVM虚拟机最大使用内存为：" + maxMemory * 4.0 / 1024 + "G");

        try {
            Thread.sleep(100000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
