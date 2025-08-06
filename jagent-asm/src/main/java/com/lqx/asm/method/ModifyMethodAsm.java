package com.lqx.asm.method;

import cn.hutool.core.io.FileUtil;
import org.objectweb.asm.*;
import java.io.File;

import static org.objectweb.asm.Opcodes.ASM7;

public class ModifyMethodAsm {

    public static void main(String[] args) {
        ClassReader classReader = new ClassReader(FileUtil.readBytes("/Users/mrfox/IdeaProjects/jagent-new/jagent-asm/target/classes/com/lqx/asm/method/Foo.class"));
        /**
         * 指定参数重新计算
         * ClassWriter.COMPUTE_FRAMES：包含COMPUTE_MAXS
         * COMPUTE_MAXS 指定计算运行时操作数 stack（栈帧深度）和locals(局部变量表空间)
         * 同时包含StackMapFrames
         * */
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES );

        ClassVisitor classVisitor = new ClassVisitor(ASM7, classWriter) {

            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                //删除sayHello方法
                if("math".equals(name)){
                    return null;
                }
                return super.visitMethod(access, name, descriptor, signature, exceptions);
            }

            @Override
            public void visitEnd() {
                //新增sayHello方法，变成100+1
                MethodVisitor mv = cv.visitMethod(Opcodes.ACC_PUBLIC,"math","(I)I",null,null);
                mv.visitCode();
                mv.visitVarInsn(Opcodes.ILOAD,1);
                mv.visitVarInsn(Opcodes.BIPUSH,100);
                mv.visitInsn(Opcodes.IADD);
                mv.visitInsn(Opcodes.IRETURN);

                //触发计算
                mv.visitMaxs(0,0);
                mv.visitEnd();
            }
        };

        classReader.accept(classVisitor,0);
        byte[] byteArray = classWriter.toByteArray();
        File file = FileUtil.writeBytes(byteArray, "/Users/mrfox/IdeaProjects/jagent-new/jagent-asm/src/main/java/com/lqx/asm/method/Foo.class");
        System.out.println(file.exists());
    }

}
