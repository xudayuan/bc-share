import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import visitor.ModifyStatusAdapter;

/**
 * @author : xuyupeng
 * @date 2020/3/24 16:12
 */
public class Premain {
    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader l, String name, Class c,
                                    ProtectionDomain d, byte[] b) {
                ClassReader cr = new ClassReader(b);
                ClassWriter cw = new ClassWriter(cr, 0);
                ClassVisitor cv = new ModifyStatusAdapter(0, cw);
                cr.accept(cv, 0);
                return cw.toByteArray();
            }
        });
    }
}
