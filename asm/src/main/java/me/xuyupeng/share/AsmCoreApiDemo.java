package me.xuyupeng.share;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.IOException;

/**
 * @author : xuyupeng.xuyupeng
 * @date 2020/3/22 12:47
 */
public class AsmCoreApiDemo {
    public static void main(String[] args) {
        //打印类的字节码
        printClassByteCode();
    }

    /**
     * 打印类的字节码
     */
    public static void printClassByteCode() {
        try {
            ClassWriter cw = new ClassWriter(0);
            ClassReader cr = new ClassReader(DemoClazz.class.getName());
            cr.accept(cw, 0);
            byte[] bytes = cw.toByteArray();
            int i = 16;
            for (byte aByte : bytes) {
                System.out.printf("%02x ", aByte);
                if (--i == 0) {
                    System.out.println("");
                    i = 16;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
