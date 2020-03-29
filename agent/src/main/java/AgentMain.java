import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

import former.AsmCoreClassFileTransformer;
import former.JaClassFileFormer;
import me.xuyupeng.share.DemoClazz;

/**
 * @author : xuyupeng.xuyupeng
 * @date 2020/3/24 16:12
 */
public class AgentMain {
    public static void agentmain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new AsmCoreClassFileTransformer(), true);
        try {
            inst.retransformClasses(DemoClazz.class);
        } catch (UnmodifiableClassException e) {
            e.printStackTrace();
        }
    }
}
