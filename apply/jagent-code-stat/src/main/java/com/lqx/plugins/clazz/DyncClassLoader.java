package com.lqx.plugins.clazz;

import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;

public class DyncClassLoader extends ClassLoader {
    private static  Map<String,Class<?>> classes = new HashMap<>();

    public DyncClassLoader(ClassLoader systemClassLoader) {
        super(systemClassLoader);
    }


    @Override
    @SneakyThrows
    public Class<?> findClass(String name){
        if(classes.containsKey(name)) {
            return classes.get(name);
        }
        return super.findClass(name);
    }

    @Override
    @SneakyThrows
    public synchronized Class<?> loadClass(String name, boolean resolve)  {
        Class<?> loadedClass = null;
        try {
            loadedClass = findLoadedClass(name);
            if (loadedClass != null) {
                return loadedClass;
            }

            // 优先从parent（SystemClassLoader）里加载系统类，避免抛出ClassNotFoundException
            if (name != null && (name.startsWith("sun.") || name.startsWith("java."))) {
                return super.loadClass(name, resolve);
            }
            try {
                loadedClass = findClass(name);
                if (resolve) {
                    resolveClass(loadedClass);
                }
                return loadedClass;
            } catch (Exception e) {
                // ignore
            }
            loadedClass = super.loadClass(name, resolve);
            return loadedClass;
        }finally {
            classes.put(name, loadedClass);
        }
    }


}
