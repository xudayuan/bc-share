package me.xuyupeng.share;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.IOException;
import java.lang.reflect.Field;

import jdk.internal.org.objectweb.asm.util.TraceClassVisitor;
import me.xuyupeng.share.asm.SimpleClassVisitor;
import me.xuyupeng.share.utils.AsmClassLoader;
import me.xuyupeng.share.utils.MyClassLoader;

/**
 * @author : xuyupeng
 * @date 2020/3/22 12:47
 */
public class AsmCoreApiDemo {
    public static void main(String[] args) {
        //打印类的字节码
        // printClassByteCode();
        //生成一个新类
        // generateNewClass();
        //重写类加载器，对类动态修改
        //loanAsmClass();
        //core api optimize
        optimize();

    }

    /**
     * 打印类的字节码
     */
    public static void printClassByteCode() {
        ClassWriter cw = new ClassWriter(0);
        try {
            ClassReader cr = new ClassReader("me.xuyupeng.share.DemoClazz");
            cr.accept(cw, 0);
            cw.visitEnd();
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

    /**
     * 用类加载器加载新的类
     */
    private static void loanAsmClass() {
        AsmClassLoader loader = new AsmClassLoader();
        try {
            Class<?> aClass = loader.loadClass("me.xuyupeng.share.DemoClazzASM");
            System.out.println(aClass.getClassLoader());
            Field generateField = aClass.getDeclaredField("GENERATE_FIELD");
            System.out.println(generateField);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * 生成一个类
     */
    private static void generateNewClass() {
        try {
            ClassWriter cw = new ClassWriter(0);
            TraceClassVisitor
            SimpleClassVisitor cp = new SimpleClassVisitor(Opcodes.ASM7, cw);
            ClassReader cr = new ClassReader("me.xuyupeng.share.DemoClazz");
            cr.accept(cp, 0);
            //define class header
            cw.visit(Opcodes.V1_5, Opcodes.ACC_PUBLIC, "me/xuyupeng/share/DemoClazzGenerate", null, "me/xuyupeng/share/DemoClazz", null);
            //define a public final static int field with default value 1
            cw.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC, "GENERATE_FIELD", "I", null, 1).visitEnd();
            //define a public abstract method
            cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT, "getI", "(Ljava/lang/Integer;)I", null, null).visitEnd();
            cw.visitEnd();
            byte[] b = cw.toByteArray();
            Class c = new MyClassLoader().defineClass("me.xuyupeng.share.DemoClazzGenerate", b);
            System.out.println(c.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * core api optimize Copy
     */
    private static void optimize() {
        try {
            ClassReader cr = new ClassReader("me.xuyupeng.share.DemoClazz");
            ClassWriter cw = new ClassWriter(cr, 0);
            SimpleClassVisitor cp = new SimpleClassVisitor(Opcodes.ASM7, cw);
            cr.accept(cp, 0);
            //define class header
            cw.visitEnd();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
