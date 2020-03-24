package me.xuyupeng.share;

/**
 * 用于演示字节码的类
 *
 * @author : xuyupeng
 * @date 2020/3/21 16:46
 */
public class DemoClazzV {
    public static Integer vIn = 5;

    public void compute(int a, int b) {
        int x = vIn;
        int c = x + a + b;
        vIn = c;
    }
}
