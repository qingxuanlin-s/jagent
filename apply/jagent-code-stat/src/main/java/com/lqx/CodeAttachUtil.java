package com.lqx;

import com.lqx.plugins.StackStatPlugin;
import com.lqx.plugins.Tuple2;
import com.lqx.plugins.clazz.DyncClassLoader;
import com.lqx.plugins.collect.StackRunner;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class CodeAttachUtil {

    public static DyncClassLoader dyncClassLoader = new DyncClassLoader(Thread.currentThread().getContextClassLoader());

    private static AtomicBoolean inited = new AtomicBoolean(false);

    public static final ScheduledExecutorService EXECUTOR = Executors.newScheduledThreadPool(1);

    @lombok.SneakyThrows
    public static Boolean methodInvokeStat(String cName,int second){
        try {
            //cxxx#xxxx
            String[] split = cName.split("#");
            if(split.length < 2){
                throw new IllegalArgumentException("不能缺少方法名");
            }

            if(inited.compareAndSet(false,true)){
                ByteBuddyAgent.install();
            }

            // 保存原始类定义
            Class<?> fooClass;
            try {
                fooClass = Class.forName(split[0]);
            }catch (Exception e){
                throw new IllegalArgumentException("类不存在");
            }

            String methodTmp = null;
            Method[] methods = fooClass.getMethods();
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];
                if(method.toString().contains(split[0] + "." + split[1])){
                    methodTmp = method.toString();
                }
            }
            if(methodTmp == null){
                throw new IllegalArgumentException("方法不存在");
            }


            byte[] originalClassDefinition = new ByteBuddy()
                    .redefine(fooClass)
                    .make()
                    .getBytes();

            // 动态增强类
            new ByteBuddy()
                    .redefine(fooClass)
                    .visit( Advice.to(StackStatPlugin.class).on(ElementMatchers.named(split[1])))
                    .make()
                    .load(fooClass.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());

            System.out.println("注入成功：" + cName);

            String finalMethodTmp = methodTmp;

            EXECUTOR.schedule(()->{
                System.out.println("撤销注入，开始汇总结果：" + cName);
                // 动态撤销增强
                new ByteBuddy()
                        .redefine(fooClass, ClassFileLocator.Simple.of(fooClass.getName(), originalClassDefinition))
                        .make()
                        .load(fooClass.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());

                Tuple2<String, Map<String, AtomicInteger>> stat = StackRunner.getStat(finalMethodTmp);
                System.out.println("当前共调用：" + stat.second.values().stream()
                        .mapToInt(AtomicInteger::get)
                        .sum() + "次");

                System.out.println("---------------------------------------");
                for (String string : stat.second.keySet()) {
                    System.out.println("当前链路：" );
                    System.out.println(string);
                    System.out.println("调用次数：" + stat.second.get(string));
                    System.out.println("---------------------------------------");
                }
                System.out.println("调用监控"+ stat);

            },second, TimeUnit.SECONDS);

        } catch (Exception e){
            e.printStackTrace();
        }

        return true;
    }

}
