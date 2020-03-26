package me.xuyupeng.share;

/**
 * 用于演示字节码的类
 *
 * @author : xuyupeng
 * @date 2020/3/21 16:46
 */
public class DemoClazz {
    public static volatile Integer iv = 5;
    public static Integer i = 8;

    public void compute() {
        int a = 6;
        int b = 29;
        iv = iv + a + b;
        int c = iv + 1;
        i = i + a + b;
        System.out.println(i);
    }

    public void enhanceCompute() {
        long time = System.currentTimeMillis();
        int a = 6;
        int b = 29;
        iv = iv + a + b;
        int c = iv + 1;
        i = i + a + b;
        System.out.println(i);
        time = System.currentTimeMillis() - time;
        System.out.println(time);
    }

    public boolean getStatus() {
        return 8 == i;
    }

    public boolean getTrue() {
        return true;
    }
}
