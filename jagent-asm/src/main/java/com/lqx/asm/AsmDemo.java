package com.lqx.asm;


import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.IOException;
import java.lang.reflect.Method;

public class AsmDemo {

    public static void main(String[] args) throws Exception {
        // 1. 定义 ClassReader
        // 读取我们想要修改的类的字节码
        String className = OriginalClass.class.getName();
        ClassReader classReader = new ClassReader(className);

        // 2. 定义 ClassWriter
        // ClassWriter.COMPUTE_FRAMES 会自动计算操作数栈和局部变量表的大小，对我们来说很方便
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        // 3. 定义 ClassVisitor
        // 将我们自定义的 Visitor 与 ClassWriter 关联起来
        LogMethodClassVisitor classVisitor = new LogMethodClassVisitor(classWriter);

        // 4. 连接与执行
        // ClassReader.EXPAND_FRAMES 用于支持 COMPUTE_FRAMES
        // accept 方法将事件从 reader 发送到 visitor，最终到 writer
        classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES);

        // 5. 获取修改后的字节码
        byte[] modifiedBytecode = classWriter.toByteArray();

        // 6. 使用自定义 ClassLoader 加载修改后的类
        MyClassLoader myClassLoader = new MyClassLoader();
        Class<?> modifiedClass = myClassLoader.defineClass(className, modifiedBytecode);

        // 7. 反射调用，验证结果
        Object instance = modifiedClass.getDeclaredConstructor().newInstance();
        Method method = modifiedClass.getMethod("sayHello");
        method.invoke(instance);
    }

    /**
     * 自定义类加载器，用于加载我们动态生成的字节码
     */
    static class MyClassLoader extends ClassLoader {
        public Class<?> defineClass(String name, byte[] b) {
            return defineClass(name, b, 0, b.length);
        }
    }
}