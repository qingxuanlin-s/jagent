package com.lqx;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import lombok.SneakyThrows;

public class ReCodeUtil {

    /***
     * 修改类中的方法体
     * */
    @SneakyThrows
    public static void recode() {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get("com.lqx.Foo");

        CtMethod personFly = cc.getDeclaredMethod("sayHelloFoo");
        personFly.setBody("{return \"Hello, modified!\";}");
        cc.toClass();
        Foo myClass = new Foo();
        System.out.println(myClass.sayHelloFoo());

    }

    /***
     * 修改类中的方法体
     * */
    @SneakyThrows
    public static void recodeLoadClass() {
        ClassPool classPool = ClassPool.getDefault();

        // 获取已加载的类
        CtClass targetClass = classPool.get("com.lqx.Foo");

        // 找到需要修改的方法
        CtMethod targetMethod = targetClass.getDeclaredMethod("sayHelloFoo");

        // 在现有方法中插入新的代码
        targetMethod.insertBefore("{ System.out.println(\"Before\"); }");
        targetMethod.insertAfter("{ System.out.println(\"After\"); }");
        // 创建一个新的 ClassLoader
        ClassLoader cl = new Loader(classPool);
        // 使用新的 ClassLoader 定义新类
        targetClass.toClass(cl, null);

        Foo myClass = new Foo();
        System.out.println(myClass.sayHelloFoo());
    }



    static class Loader extends ClassLoader {
        private ClassPool classPool;

        public Loader(ClassPool cp) {
            classPool = cp;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            try {
                CtClass cc = classPool.get(name);
                byte[] bytecode = cc.toBytecode();
                return defineClass(name, bytecode, 0, bytecode.length);
            } catch (Exception e) {
                throw new ClassNotFoundException(e.getMessage(), e);
            }
        }
    }

}
