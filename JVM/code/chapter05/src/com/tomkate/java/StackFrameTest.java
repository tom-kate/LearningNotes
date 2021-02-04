package com.tomkate.java;

import java.util.Date;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/2/4 16:45
 */
public class StackFrameTest {
    private int count = 1;

    /**
     * 模拟方法进栈出栈，先进后出，后进先出
     *
     * @param args
     */
    public static void main(String[] args) {
        StackFrameTest stackFrameTest = new StackFrameTest();
        stackFrameTest.method01();
    }

    public void method01() {
        System.out.println("method1开始执行......");
        method02();
        System.out.println("method1执行结束......");
    }

    public int method02() {
        int a = 10;
        System.out.println("method2开始执行......");
        int c = (int) method03();
        System.out.println("method2执行结束......");
        return a + c;
    }

    public double method03() {
        double b = 20.0;
        System.out.println("method3开始执行......");
        System.out.println("method3执行结束......");
        return b;
    }

    /**
     * 练习：字节码查看
     */
    public static void test() {
        StackFrameTest stackFrameTest = new StackFrameTest();
        Date date = new Date();
        int a = 100;
        System.out.println(a);
        //因为this变量不存在于当前局部变量表中！！！
        //System.out.println(this.count);
    }

    //关于slot的使用的理解
    public StackFrameTest() {
        this.count = 2;
    }

    public void test1() {
        StackFrameTest stackFrameTest = new StackFrameTest();
        Date date = new Date();
        int a = 100;
        System.out.println(a);
        //因为this变量不存在于当前局部变量表中！！！
        //System.out.println(this.count);
    }

    /**
     * Slot的复用
     */
    public void test2() {
        int a = 0;
        {
            int b = 0;
            b = a + 1;
        }
        //变量c使用之前已经销毁的变量b占据的slot的位置
        int c = a + 1;
    }

    /**
     * 变量的分类：
     * 按照数据类型分：1.基本数据类型  2.引用数据类型
     * 按照类中声明的位置分类:
     *     1.成员变量：在使用前，都经历过默认初始化赋值
     *          类变量（static修饰的）：
     *          linking的prepare阶段：给类变量默认赋值-->
     *          initial阶段：给类变量显示赋值即静态代码块赋值
     *
     *          实例变量：随着对象的创建，会在堆空间中分配实例变量空间，并进行默认赋值
     *     2.局部变量：在使用前，必须要进行显示赋值！否则，编译不通过
     */
    public void test3(){
        int num;
//        System.out.println(num);
        //错误信息：变量num未进行初始化
    }
}
