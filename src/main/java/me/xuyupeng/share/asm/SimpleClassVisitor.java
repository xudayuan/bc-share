package me.xuyupeng.share.asm;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 简单ClassVisitor示例
 *
 * @author : xuyupeng
 * @date 2020/3/22 14:02
 */
public class SimpleClassVisitor extends ClassVisitor implements Opcodes {


    public SimpleClassVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        System.out.println("minor version :" + (version >> 16));
        System.out.println("major version : " + (version & 0x00FF));
        System.out.printf("access = %08x\n", access);
        System.out.println("signature = " + signature);
        System.out.println("superName = " + superName);
    }

    @Override
    public void visitSource(String source, String debug) {
        System.out.println("visitSource | " + source + " | " + debug);
    }

    @Override
    public void visitOuterClass(String owner, String name, String descriptor) {
        System.out.println("visitOuterClass | " + owner + " | " + name + " | " + descriptor);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        System.out.println("visitAnnotation |" + " | " + descriptor + " | " + visible);
        return super.visitAnnotation(descriptor, visible);
    }

    @Override
    public void visitAttribute(Attribute attribute) {
        System.out.println("visitAttribute | " + attribute);
        super.visitAttribute(attribute);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        System.out.println("visitField | " + access + " | " + name + " | " + descriptor + " | " + signature + " | " + value);
        return cv.visitField(access, name, descriptor, signature, value);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        System.out.println("visitMethod | " + " | " + access + " | " + name + " | " + descriptor + " | " + signature + "  | " + exceptions);
        return cv.visitMethod(access, name, descriptor, signature, exceptions);
    }

    @Override
    public void visitEnd() {
        System.out.println("visitEnd");
        super.visitEnd();
    }
}
