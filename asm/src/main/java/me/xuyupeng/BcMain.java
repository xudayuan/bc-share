package me.xuyupeng;

import java.lang.management.ManagementFactory;

import me.xuyupeng.share.DemoClazz;

/**
 * 被增强的主进程
 *
 * @author : xuyupeng
 * @date 2020/3/20 10:52
 */
public class BcMain {
    public static void main(String[] args) {
        System.out.println(ManagementFactory.getRuntimeMXBean().getName());
        DemoClazz dc = new DemoClazz();
        new Thread(() -> {
            while (true) {
                dc.compute();
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
