import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

import transformer.AsmCoreClassFileTransformer;
import transformer.AsmTreeClassFileTransformer;
import transformer.JaClassFileFormer;
import me.xuyupeng.share.DemoClazz;

/**
 * agent入口
 *
 * @author : xuyupeng.xuyupeng
 * @date 2020/3/24 16:12
 */
public class AgentMain {
    public static void agentmain(String agentArgs, Instrumentation inst) {
        //使用asm-core实现的ClassFileTransformer
        ClassFileTransformer accft = new AsmCoreClassFileTransformer();
        //使用asm-tree实现的ClassFileTransformer
        ClassFileTransformer atcft = new AsmTreeClassFileTransformer();
        //使用javassit实现的ClassFileTransformer
        ClassFileTransformer jcft = new JaClassFileFormer();
        inst.addTransformer(atcft, true);
        try {
            inst.retransformClasses(DemoClazz.class);
        } catch (UnmodifiableClassException e) {
            e.printStackTrace();
        }
    }
}
