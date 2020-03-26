package me.xuyupeng.share.utils;

/**
 * 自定义类加载器
 *
 * @author : xuyupeng
 * @date 2020/3/22 18:35
 */
public class MyClassLoader extends ClassLoader {
    public Class defineClass(String name, byte[] b) {
        return defineClass(name, b, 0, b.length);
    }
}
