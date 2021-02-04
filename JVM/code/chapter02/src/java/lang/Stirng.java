package java.lang;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/2/4 16:17
 */
public class Stirng {
    static {
        System.out.println("自定义String类");
    }

    //委派bootStrap ClassLoader 加载到lang包下String String类没由main方法导致报错 找不到main方法
    //错误: 在类 java.lang.String 中找不到 main 方法
    //沙箱保护机制
    public static void main(String[] args) {
        System.out.println("hello！String");
    }
}
