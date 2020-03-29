package modifier;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.NotFoundException;

import java.io.IOException;

import me.xuyupeng.share.DemoClazz;

/**
 * 用javassit实现的字节码转换器
 *
 * @author : xuyupeng
 * @date 2020/3/29 17:59
 */
public class JaClassModifier {
    public static byte[] transform(String className, byte[] cbf){
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
        return cbf;
    }
}
