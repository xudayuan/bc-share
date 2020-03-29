package xuyupeng;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.IOException;

/**
 * @author : xuyupeng
 * @date 2020/3/26 23:11
 */
public class Attacher {
    public static void main(String[] args) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        VirtualMachine vm = VirtualMachine.attach("8020");
        vm.loadAgent("E:/bc-share/agent/target/agent-1.0-SNAPSHOT.jar");
    }
}
