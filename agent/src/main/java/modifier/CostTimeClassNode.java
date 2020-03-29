package modifier;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static org.objectweb.asm.Opcodes.ASM5;
import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.GETSTATIC;
import static org.objectweb.asm.Opcodes.INVOKESPECIAL;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.objectweb.asm.Opcodes.LLOAD;
import static org.objectweb.asm.Opcodes.LSTORE;
import static org.objectweb.asm.Opcodes.LSUB;
import static org.objectweb.asm.Opcodes.NEW;
import static org.objectweb.asm.Opcodes.RETURN;

/**
 * 添加耗时计时的ClassNode
 * @author : xuyupeng
 * @date 2020/3/29 15:52
 */
public class CostTimeClassNode extends ClassNode {
    public CostTimeClassNode(ClassVisitor cv){
        super(ASM5);
        this.cv = cv;
    }
    @Override
    public void visitEnd() {
        //当调用到此方法时ClassNode中的类的各个部分已经初始化完毕，这里对其进行更改
        Iterator iterator = this.methods.iterator();
        while (iterator.hasNext()){
            MethodNode m = (MethodNode)iterator.next();
            List lvs = m.localVariables;
            if("compute".equals(m.name)){
                //对compute方法进行转化添加耗时
                //定义两个label，用于限定局部变量的作用范围
                LabelNode labelFirst  = new LabelNode();
                LabelNode labelLast = new LabelNode();
                //先添加局部变量 long time
                int varIndex = m.localVariables.size();
                InsnList instructions = m.instructions;
                //添加前一部分
                InsnList i2 = new InsnList();
                //先添加标签
                i2.add(labelFirst);
                i2.add(new MethodInsnNode(INVOKESTATIC, Type.getType(System.class).getInternalName(), "currentTimeMillis", "()J", false));
                i2.add(new VarInsnNode(LSTORE, varIndex));
                instructions.insertBefore(instructions.getFirst(),i2);
                ListIterator itr = instructions.iterator();
                while(itr.hasNext()){
                    AbstractInsnNode in = (AbstractInsnNode) itr.next();
                    int op = in.getOpcode();
                    if(op == RETURN){
                        InsnList i1 = new InsnList();
                        i1.add(new MethodInsnNode(INVOKESTATIC, Type.getType(System.class).getInternalName(), "currentTimeMillis", "()J", false));
                        i1.add(new VarInsnNode(LLOAD,varIndex));
                        i1.add(new InsnNode(LSUB));
                        i1.add(new VarInsnNode(LSTORE,varIndex));
                        i1.add(new FieldInsnNode(GETSTATIC, Type.getType(System.class).getInternalName(), "out", Type.getType(PrintStream.class).getDescriptor()));
                        i1.add(new TypeInsnNode(NEW,Type.getType(StringBuilder.class).getInternalName()));
                        i1.add(new InsnNode(DUP));
                        i1.add(new MethodInsnNode(INVOKESPECIAL, Type.getType(StringBuilder.class).getInternalName(), "<init>","()V",false));
                        i1.add(new LdcInsnNode("compute cost millis : "));
                        i1.add(new MethodInsnNode(INVOKEVIRTUAL,Type.getType(StringBuilder.class).getInternalName(),
                                "append","(Ljava/lang/String;)Ljava/lang/StringBuilder;",false));
                        i1.add(new VarInsnNode(LLOAD,varIndex));
                        i1.add(new MethodInsnNode(INVOKEVIRTUAL,Type.getType(StringBuilder.class).getInternalName(),
                                "append","(J)Ljava/lang/StringBuilder;",false));
                        i1.add(new MethodInsnNode(INVOKEVIRTUAL,Type.getType(StringBuilder.class).getInternalName(),
                                "toString","()Ljava/lang/String;",false));
                        i1.add(new MethodInsnNode(INVOKEVIRTUAL,Type.getType(PrintStream.class).getInternalName(),
                                "println","(Ljava/lang/String;)V",false));
                        //添加最后一个标签，用于定义局部变量作用范围
                        instructions.insertBefore(in,i1);
                    }
                }
                LocalVariableNode localVariableNode = (LocalVariableNode) m.localVariables.get(m.localVariables.size() - 1);
                m.localVariables.add(new LocalVariableNode("time",Type.LONG_TYPE.getDescriptor(),null, labelFirst,
                        localVariableNode.end,varIndex));
                m.maxStack += 2;
                m.maxLocals += 2;
            }
        }
        //上层ClassVisitor最后调用此方法，在这里将事件传给下一层ClassVisitor
        accept(cv);
    }
}
