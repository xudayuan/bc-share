package xuyupeng;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import sun.jvmstat.monitor.MonitorException;
import sun.jvmstat.monitor.MonitoredHost;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

/**
 * @author : xuyupeng
 * @date 2020/3/26 23:11
 */
public class Attacher {
    public static void main(String[] args) throws IOException, AttachNotSupportedException,
            AgentLoadException, AgentInitializationException, MonitorException, URISyntaxException {
        //获取当前主机上的所有进程Id
        MonitoredHost mh = MonitoredHost.getMonitoredHost("localhost");
        Set<Integer> pids = mh.activeVms();
        for (Integer pid : pids) {
            VirtualMachine vm = VirtualMachine.attach(String.valueOf(pid));
            //找到指定进程进行增强
            if ("me.xuyupeng.BcMain".equals(vm.getSystemProperties().getProperty("sun.java.command"))) {
                System.out.println("开始增强 pid = " + pid);
                vm.loadAgent("E:/bc-share/agent/target/agent-1.0-SNAPSHOT.jar");
            }
        }

    }
}
