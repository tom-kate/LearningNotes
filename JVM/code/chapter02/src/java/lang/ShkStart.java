package java.lang;

/**
 * @author Tom
 * @version 1.0
 * @date 2021/1/21 21:27
 */
public class ShkStart {
    public static void main(String[] args) {
        //java.lang.SecurityException: Prohibited package name: java.lang
        //引导类加载器 防止重自定义java.lang包不存在的方法
        System.out.println("hello!ShkStart");
    }
}
