package com.tomkate.java;

/**
 * 手动GC来理解不可达对象的回收
 * -XX:+PrintGCDetails
 * 测试发现 FULL GC 将所有能回收的都移至老年代
 * 观察时主要观察内存是否被释放 而不是是否被移至老年代
 *
 * @author Tom
 * @version 1.0
 * @date 2021/2/18 15:26
 */
public class LocalVarGC {

    /**
     * 触发Minor GC没有回收对象，然后在触发Full GC将该对象存入old区
     */
    public void localvarGC1() {
        byte[] buffer = new byte[10 * 1024 * 1024];//10M
        System.gc();
    }

    /**
     * 触发YoungGC的时候，已经被回收了
     */
    public void localvarGC2() {
        byte[] buffer = new byte[10 * 1024 * 1024];//10M
        buffer = null;
        System.gc();
    }

    /**
     * 不会被回收，因为它还存放在局部变量表索引为1的槽中
     * 没有出栈
     */
    public void localvarGC3() {
        {
            byte[] buffer = new byte[10 * 1024 * 1024];
        }
        System.gc();
    }

    /**
     * 会被回收，因为它还存放在局部变量表索引为1的槽中，但是后面定义的value把这个槽给替换了
     */
    public void localvarGC4() {
        {
            byte[] buffer = new byte[10 * 1024 * 1024];
        }
        int value = 10;
        System.gc();
    }

    /**
     * localvarGC5中的数组已经被回收
     */
    public void localvarGC5() {
        localvarGC1();
        System.gc();
    }

    public static void main(String[] args) {
//        LocalVarGC localVarGC = new LocalVarGC();
//        localVarGC.localvarGC1();
        StringBuilder str = new StringBuilder("tomkate");
        StringBuilder str1 = str;

        str = null;
        System.gc();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(str1);
        System.out.println(str);
    }
}
