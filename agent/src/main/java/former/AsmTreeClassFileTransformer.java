package former;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import me.xuyupeng.share.DemoClazz;
import visitor.CostTimeClassNode;

/**
 * @author : xuyupeng
 * @date 2020/3/29 15:56
 */
public class AsmTreeClassFileTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (DemoClazz.class.getName().equals(className)) {
            ClassReader cr = new ClassReader(classfileBuffer);
            ClassWriter cw = new ClassWriter(0);
            CostTimeClassNode cn = new CostTimeClassNode(cw);
            cr.accept(cn, 0);
            return cw.toByteArray();
        }
        return classfileBuffer;
    }
}
