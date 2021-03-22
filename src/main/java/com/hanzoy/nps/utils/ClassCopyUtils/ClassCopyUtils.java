package com.hanzoy.nps.utils.ClassCopyUtils;

import java.lang.reflect.Field;

public class ClassCopyUtils {
    public static void ClassCopy(Object dest, Object src){
        //获取拷贝源的字段
        Class<?> srcClass = src.getClass();
        Field[] srcClassDeclaredFields = srcClass.getDeclaredFields();

        //获取拷贝目标的字段
        Class<?> destClass = dest.getClass();
        Field[] destClassDeclaredFields = destClass.getDeclaredFields();

        for (Field destClassDeclaredField : destClassDeclaredFields) {
            //开启字段强制访问
            destClassDeclaredField.setAccessible(true);

            //获取字段名
            String name = destClassDeclaredField.getName();
            if(destClassDeclaredField.isAnnotationPresent(CopyProperty.class)){
                name = destClassDeclaredField.getAnnotation(CopyProperty.class).value();
            }
            //遍历拷贝源字段
            for (Field srcClassDeclaredField : srcClassDeclaredFields) {
                //拷贝源开启字段强制访问
                srcClassDeclaredField.setAccessible(true);

                if(srcClassDeclaredField.getName().equals(name)){
                    try {
                        destClassDeclaredField.set(dest, srcClassDeclaredField.get(src));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }
}
