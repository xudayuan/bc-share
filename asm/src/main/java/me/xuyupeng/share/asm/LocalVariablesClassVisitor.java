package me.xuyupeng.share.asm;


import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.LocalVariablesSorter;


/**
 * @author : xuyupeng
 * @date 2020/3/27 13:45
 */
public class LocalVariablesClassVisitor extends ClassVisitor {
    public LocalVariablesClassVisitor(int api) {
        super(api);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        return new LocalVariablesMethodVisitor(access,desc,mv);

    }
    class LocalVariablesMethodVisitor extends LocalVariablesSorter {
        public LocalVariablesMethodVisitor(int access, String desc, MethodVisitor mv) {
            super(access, desc, mv);
        }

        @Override
        public int newLocalMapping(Type type) {
            return super.newLocalMapping(type);
        }
    }
}
