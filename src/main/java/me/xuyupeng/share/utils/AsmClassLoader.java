package me.xuyupeng.share.utils;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

/**
 * @author : xuyupeng
 * @date 2020/3/22 22:56
 */
public class AsmClassLoader extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        System.out.println("AsmClassLoader start...");
        if (name.endsWith("ASM")) {
            ClassWriter cw = new ClassWriter(0);
            //define class header
            cw.visit(Opcodes.V1_5, Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC, name.replace('.', '/'), null, name.substring(0, name.length() - 3).replace('.', '/'), null);
            //define a public final static int field with default value 1
            cw.visitField(Opcodes.ACC_PUBLIC, "GENERATE_FIELD", "I", null, 1).visitEnd();
            cw.visitEnd();
            byte[] b = cw.toByteArray();
            return super.defineClass(name, b, 0, b.length);
        }
        return super.findClass(name);
    }
}
