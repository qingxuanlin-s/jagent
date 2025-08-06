package com.lqx.asm;


import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.ClassVisitor;

public class LogMethodClassVisitor extends ClassVisitor {

    public LogMethodClassVisitor(ClassVisitor classVisitor) {
        // 注意这里的 Opcodes.ASM7 是为了兼容最新的 Java 版本
        super(Opcodes.ASM7, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        // 首先，让父类（链条中的下一个 visitor）处理
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);

        // 我们只关心 "sayHello" 方法
        if (mv != null && "sayHello".equals(name)) {
            // 返回我们自定义的 MethodVisitor 来增强这个方法
            return new LogMethodVisitor(mv);
        }
        
        // 其他方法不处理，直接返回父类创建的 mv
        return mv;
    }

    /**
     * 自定义的 MethodVisitor，用于向方法体中添加代码
     */
    private static class LogMethodVisitor extends MethodVisitor {

        public LogMethodVisitor(MethodVisitor methodVisitor) {
            super(Opcodes.ASM7, methodVisitor);
        }

        // 当方法代码开始访问时调用
        @Override
        public void visitCode() {
            // 调用父类 visitCode()，以确保原始代码的指令能被正确访问
            super.visitCode();

            // --- 开始注入我们的代码 ---
            // 打印 "Entering method: sayHello"
            // 获取 System.out
            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            // 将字符串常量压入栈顶
            mv.visitLdcInsn("Entering method: sayHello");
            // 调用 PrintStream.println(String)
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            // --- 注入代码结束 ---
        }

        // 当访问到方法的返回指令时调用 (如 IRETURN, LRETURN, ARETURN, RETURN)
        @Override
        public void visitInsn(int opcode) {
            // 我们只在方法正常返回前插入代码
            // Opcodes.ATHROW 用于处理异常情况，这里我们不处理
            if ((opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN) || opcode == Opcodes.ATHROW) {
                // --- 开始注入我们的代码 ---
                 // 打印 "Exiting method: sayHello"
                mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                mv.visitLdcInsn("Exiting method: sayHello");
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
                // --- 注入代码结束 ---
            }
            // 调用父类 visitInsn，确保返回指令本身被写入
            super.visitInsn(opcode);
        }
    }
}