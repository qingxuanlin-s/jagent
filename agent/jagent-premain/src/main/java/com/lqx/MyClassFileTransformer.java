package com.lqx;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class MyClassFileTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {

        //Bugs will be triggered when executed here, but debugging won't
        System.out.println("你好");

        if (!"com/lqx/MyTest".equals(className)) {
            return classfileBuffer; // Return immediately for all other classes.
        }

        ClassReader classReader = new ClassReader(classfileBuffer);
        ClassWriter cw = new ClassWriter(classReader, ClassWriter.COMPUTE_FRAMES);
        MyClassVisitor cv = new MyClassVisitor(cw);
        classReader.accept(cv, ClassReader.SKIP_FRAMES | ClassReader.SKIP_DEBUG);
        return cw.toByteArray();

    }
}
