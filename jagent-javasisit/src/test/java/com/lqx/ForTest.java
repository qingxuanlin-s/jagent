package com.lqx;


import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.lqx.fort.TestFor;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import lombok.SneakyThrows;
import org.junit.BeforeClass;
import org.junit.Test;


public class ForTest {

    @BeforeClass
    public static void init() {
        try {
            ClassPool cp = ClassPool.getDefault();
            CtClass cc = cp.get("com.lqx.fort.TestFor");
            //解冻
            cc.defrost();
            CtMethod m = cc.getDeclaredMethod("hello");

            //m.insertBefore("   for(int i = 0; i<x.size(); i++){           yBuilder.append(\" \").append(x.get(i));  }");

            m.insertBefore("  for (Integer integer : x) {       yBuilder.append(\" \").append(integer);  }");


            cc.writeFile("/Users/mrfox/IdeaProjects/jagent-new/classes");
            cc.toClass(ForTest.class.getClassLoader());
        } catch (Exception e) {
            System.out.println(Throwables.getStackTraceAsString(e));
        }

    }

    @SneakyThrows
    @Test
    public void test() {
        System.out.println(new TestFor().hello(Lists.newArrayList(1,2,3,4),new StringBuilder("ceshi")));
    }
}