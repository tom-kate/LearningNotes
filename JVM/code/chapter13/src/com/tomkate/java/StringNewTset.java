package com.tomkate.java;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/2/16 21:06
 */
public class StringNewTset {
    public static void main(String[] args) {
//        String str = new String("a");
        /**
         * 对象1：new StringBuilder()
         * 对象2：new String("a")
         * 对象3：常量池中的"a"
         * 对象4：new String("b")
         * 对象5：常量池中的"b"
         *
         * 深入剖析：StringBuilder的toString()
         *      对象9： new String("ab")
         *      强调一下，toString()的调用，在字符串常量池中，没有生成"ab"
         */
        String str = new String("a") + new String("b");
    }
}
