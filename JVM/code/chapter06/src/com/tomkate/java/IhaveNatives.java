package com.tomkate.java;

/**
 * 本地方法
 *
 * @author Tom
 * @version 1.0
 * @date 2021/2/5 18:39
 */
public class IhaveNatives {
    public native void Native1(int x);

    native static public long Native2();

    private native synchronized  float Native3(Object o);

    native void Natives(int[] ary) throws Exception;
}
