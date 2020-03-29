package former;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.NotFoundException;

import org.objectweb.asm.Type;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import me.xuyupeng.share.DemoClazz;

/**
 * @author : xuyupeng
 * @date 2020/3/27 11:34
 */
public class JaClassFileFormer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        try {
            ClassPool cp = ClassPool.getDefault();
            CtClass cc = cp.get(DemoClazz.class.getName());
            CtBehavior[] declaredBehaviors = cc.getDeclaredBehaviors();
            for (CtBehavior cd : declaredBehaviors) {
                if("compute".equals(cd.getName())){
                    cd.addLocalVariable("time",CtClass.longType);
                    cd.insertBefore("time = System.currentTimeMillis();");
                    cd.insertBefore("System.out.println(\"start compute ......\");");
                    cd.insertAfter("System.out.println(\"end compute costTime = \" + (System.currentTimeMillis() - time));");
                }
            }
            return cc.toBytecode();
        } catch (NotFoundException | CannotCompileException | IOException e) {
            e.printStackTrace();
        }
        return classfileBuffer;
    }
}
