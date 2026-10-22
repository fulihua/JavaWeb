package com.Fruit.util;

import java.lang.reflect.Field;

/**
 * @author fulihua
 * @date 2026/9/7 10:13 AM
 * @description
 */
public class ClassUtil {
    public static Class getClass(String entityName){
        try{
            return Class.forName(entityName);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }
    public static Object createInstance(String entityName){
        try{
            return getClass(entityName).newInstance();
        } catch(InstantiationException e){
           e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }
        return null;
    }

    public static void setProperty(Object instance,String propertyName,Object propertyValue){
        try{
            Field field = instance.getClass().getDeclaredField(propertyName);
            field.setAccessible(true);
            field.set(instance,propertyValue);
        } catch (NoSuchFieldException | IllegalAccessException e){
            e.printStackTrace();
        }
    }
}