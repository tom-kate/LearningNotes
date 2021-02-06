package com.tomkate.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 模拟OOM
 *
 * @author Tom
 * @version 1.0
 * @date 2021/2/6 14:50
 */
public class OOMTest {
    public static void main(String[] args) {
//        ArrayList<Integer> list = new ArrayList<>();
//        while (true) {
//            try {
//                Thread.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            list.add(999999999);
//        }

        ArrayList<Picture> list = new ArrayList<>();
        while (true) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list.add(new Picture(new Random().nextInt(1024 * 1024)));
        }
    }

    static class Picture {
        private byte[] pixels;

        public Picture(int length) {
            this.pixels = new byte[length];
        }
    }
}
