//package com.lqx.bcel;
//
//import com.lqx.Main;
//import com.sun.org.apache.bcel.internal.Repository;
//import com.sun.org.apache.bcel.internal.classfile.JavaClass;
//import com.sun.org.apache.bcel.internal.generic.ClassGen;
//import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
//import lombok.SneakyThrows;
///**
// *
// * 需要比较全的jdk
// * */
//public class BcelMain {
//    @SneakyThrows
//    public static void main(String[] args) {
//        JavaClass clazz = Repository.lookupClass(Main.class);
//        ClassGen classGen = new ClassGen(clazz);
//        ConstantPoolGen cPoolGen = classGen.getConstantPool(); // 常量池信息
//        System.out.println(cPoolGen.toString());
//    }
//}
