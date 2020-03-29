package transformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;
import modifier.CostTimeClassAdapter;
import static org.objectweb.asm.Opcodes.ASM5;

/**
 * @author : xuyupeng
 * @date 2020/3/27 11:38
 */
public class AsmCoreClassFileTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        ClassWriter cw = new ClassWriter(ASM5);
        CostTimeClassAdapter ctca = new CostTimeClassAdapter(ASM5, cw);
        ClassReader cr;
        try {
            cr = new ClassReader(classBeingRedefined.getName());
            cr.accept(ctca, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cw.toByteArray();
    }
}
