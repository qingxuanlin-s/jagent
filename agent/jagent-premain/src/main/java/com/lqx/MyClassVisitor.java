package com.lqx;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.ASM9;

public class MyClassVisitor extends ClassVisitor {
    public MyClassVisitor( ClassVisitor classVisitor) {
        super(ASM9, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        if(name.equals("<init>")){
            return mv;
        }

        return new MyMethodVisitor(mv,access,name,descriptor);
    }
}
