package me.xuyupeng.share;

/**
 * 用于演示字节码的类
 *
 * @author : xuyupeng
 * @date 2020/3/21 16:46
 */
public class DemoClazz {
    //静态代码块
    static {
        int a = 1;
        int b = 2;
        System.out.println(a + b);
    }

    public int compute(int a, int b) {
        int c = a + b;
        return c;
    }
}
