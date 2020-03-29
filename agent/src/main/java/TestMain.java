import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import me.xuyupeng.share.DemoClazz;
import visitor.CostTimeClassAdapter;
import visitor.CostTimeClassNode;

import static org.objectweb.asm.Opcodes.ASM5;

/**
 * @author : xuyupeng
 * @date 2020/3/27 12:06
 */
public class TestMain {
    public static void main(String[] args) throws IOException {
        File f = new File("DemoClazz.class");
        if (!f.exists() && !f.createNewFile()){
            System.out.println("文件创建失败");
        }
        System.out.println(f.getCanonicalPath());
        FileOutputStream fos = new FileOutputStream(f);
        ClassWriter cw = new ClassWriter(ASM5);
       // CostTimeClassAdapter ctca = new CostTimeClassAdapter(ASM5, cw);
        CostTimeClassNode ctca = new CostTimeClassNode(cw);
        ClassReader cr;
        cr = new ClassReader(DemoClazz.class.getName());
        cr.accept(ctca, 0);
        fos.write(cw.toByteArray());
        fos.close();
    }
}
