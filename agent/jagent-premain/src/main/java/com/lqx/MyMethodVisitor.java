package com.lqx;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.AdviceAdapter;

public class MyMethodVisitor extends AdviceAdapter {
    public MyMethodVisitor(MethodVisitor mv, int access, String name, String descriptor) {
        super(ASM9,mv, access, name, descriptor);
    }

    @Override
    protected void onMethodEnter() {
        mv.visitFieldInsn(GETSTATIC,"java/lang/System",
                "out","Ljava/io/PrintStream;");
        mv.visitLdcInsn("<<<enter " + this.getName());
        mv.visitMethodInsn(INVOKEVIRTUAL,"java/io/PrintStream",
        "println","(Ljava/lang/String;)V",false);
        super.onMethodEnter();
    }


    @Override
    protected void onMethodExit(int opcode) {
        mv.visitFieldInsn(GETSTATIC,"java/lang/System",
                "out","Ljava/io/PrintStream;");
        mv.visitLdcInsn("<<<exit " + this.getName());
        mv.visitMethodInsn(INVOKEVIRTUAL,"java/io/PrintStream",
                "println","(Ljava/lang/String;)V",false);
        super.onMethodExit(opcode);
    }
}
