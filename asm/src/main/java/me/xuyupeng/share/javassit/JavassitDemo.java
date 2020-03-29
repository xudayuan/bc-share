package me.xuyupeng.share.javassit;

import javassist.ClassPool;
import javassist.NotFoundException;

import me.xuyupeng.share.DemoClazz;

/**
 * @author : xuyupeng.xuyupeng
 * @date 2020/3/23 23:23
 */
public class JavassitDemo {
    public static void main(String[] args) {
        ClassPool cp = ClassPool.getDefault();
        try {
            System.out.println(cp.getCtClass(DemoClazz.class.getName()).getName());
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

}
