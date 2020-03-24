package me.xuyupeng.share.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author : xuyupeng
 * @date 2020/3/22 12:51
 */
public class MyClassVisitor extends ClassVisitor implements Opcodes {

    public MyClassVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    /**
     * 方法访问类，用于对方法进行访问
     */
    class MyMethodVisitor extends MethodVisitor implements Opcodes {

        public MyMethodVisitor(int api, MethodVisitor methodVisitor) {
            super(api, methodVisitor);
        }
    }
}
