package me.xuyupeng.share.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 方法计时classVisitor
 *
 * @author : xuyupeng
 * @date 2020/3/24 10:34
 */
public class costTimeClassVisitor extends ClassVisitor implements Opcodes {
    public costTimeClassVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    class costTimeMethodVisitor extends MethodVisitor implements Opcodes {

        public costTimeMethodVisitor(int api, MethodVisitor methodVisitor) {
            super(api, methodVisitor);
        }

        //重新定义方法
        @Override
        public void visitCode() {
            super.visitCode();

        }
    }
}
