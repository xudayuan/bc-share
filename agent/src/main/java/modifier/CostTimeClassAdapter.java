package modifier;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AnalyzerAdapter;
import org.objectweb.asm.commons.LocalVariablesSorter;

import java.io.PrintStream;

/**
 * 方法计时classVisitor
 *
 * @author : xuyupeng.xuyupeng
 * @date 2020/3/24 10:34
 */
public class CostTimeClassAdapter extends ClassVisitor implements Opcodes {
    //类名，方便mv使用
    private String owner;

    public CostTimeClassAdapter(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.owner = name;
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        if ("compute".equals(name)) {
            System.out.println("enhancing.....");
            AnalyzerAdapter aa =  new AnalyzerAdapter(owner, access, name, desc, mv);
            LocalVariablesSorter lvs =  new LocalVariablesSorter(access, desc, aa);
            CostTimeMethodAdapter ctma = new CostTimeMethodAdapter(Opcodes.ASM5, lvs);
            ctma.aa = aa;
            ctma.lvs = lvs;
            return ctma;
        }
        return mv;
    }

    static class CostTimeMethodAdapter extends MethodVisitor implements Opcodes {
        //添加局部变量
        public LocalVariablesSorter lvs;
        //调节stack map frames
        public AnalyzerAdapter aa;
        //新添加的局部变量在局部变量表中的索引
        private int varIndex;
        //操作数栈深度最大值
        private int maxStack;

        public CostTimeMethodAdapter(int api, MethodVisitor methodVisitor) {
            super(api, methodVisitor);
        }

        //重新定义方法
        @Override
        public void visitCode() {
            mv.visitCode();
            mv.visitMethodInsn(INVOKESTATIC, Type.getType(System.class).getInternalName(), "currentTimeMillis", "()J", false);
            varIndex = lvs.newLocal(Type.LONG_TYPE);
            mv.visitVarInsn(LSTORE, varIndex);
            maxStack = 4;
        }



        @Override
        public void visitInsn(int opcode) {
            if (opcode == RETURN || opcode == ATHROW) {
                mv.visitMethodInsn(INVOKESTATIC, Type.getType(System.class).getInternalName(), "currentTimeMillis", "()J", false);
                mv.visitVarInsn(LLOAD, varIndex);
                mv.visitInsn(LSUB);
                mv.visitVarInsn(LSTORE, varIndex);
                mv.visitFieldInsn(GETSTATIC, Type.getType(System.class).getInternalName(), "out", Type.getType(PrintStream.class).getDescriptor());
                mv.visitTypeInsn(NEW,Type.getType(StringBuilder.class).getInternalName());
                mv.visitInsn(DUP);
                mv.visitMethodInsn(INVOKESPECIAL, Type.getType(StringBuilder.class).getInternalName(),
                        "<init>","()V",false);
                mv.visitLdcInsn("compute cost millis : ");
                mv.visitMethodInsn(INVOKEVIRTUAL,Type.getType(StringBuilder.class).getInternalName(),
                        "append","(Ljava/lang/String;)Ljava/lang/StringBuilder;",false);
                mv.visitVarInsn(LLOAD,varIndex);
                mv.visitMethodInsn(INVOKEVIRTUAL,Type.getType(StringBuilder.class).getInternalName(),
                        "append","(J)Ljava/lang/StringBuilder;",false);
                mv.visitMethodInsn(INVOKEVIRTUAL,Type.getType(StringBuilder.class).getInternalName(),
                        "toString","()Ljava/lang/String;",false);
                mv.visitMethodInsn(INVOKEVIRTUAL,Type.getType(PrintStream.class).getInternalName(),
                        "println","(Ljava/lang/String;)V",false);
                maxStack = Math.max(aa.stack.size() + 4, maxStack);
            }
            mv.visitInsn(opcode);
        }

        @Override
        public void visitMaxs(int maxStack, int maxLocals) {
            super.visitMaxs(Math.max(this.maxStack, maxStack), maxLocals);
        }
    }
}
