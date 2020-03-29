package transformer;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import modifier.JaClassModifier;

/**
 * javassit实现的字节码转换器
 *
 * @author : xuyupeng
 * @date 2020/3/27 11:34
 */
public class JaClassFileFormer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        return JaClassModifier.transform(className, classfileBuffer);
    }
}
