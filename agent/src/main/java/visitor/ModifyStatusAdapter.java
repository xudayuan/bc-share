package visitor;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

/**
 * 修改状态visitor
 *
 * @author : xuyupeng
 * @date 2020/3/24 16:24
 */
public class ModifyStatusAdapter extends ClassVisitor implements Opcodes {


    public ModifyStatusAdapter(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    @Override
    public void visit(int i, int i1, String s, String s1, String s2, String[] strings) {
        super.visit(i, i1, s, s1, s2, strings);
    }
}
