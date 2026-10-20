package myssm.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

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

    public static void setSimpleProperty(Object instance,String propertyName,Object propertyValue){
        try{
            Field field = instance.getClass().getDeclaredField(propertyName);
            field.setAccessible(true);
            if(propertyValue!=null){
            field.set(instance,propertyValue);}
        } catch (NoSuchFieldException | IllegalAccessException e){
            e.printStackTrace();
        }
    }

    public static void setProperty(Object instance,String propertyName,Object propertyValue){
        try{
            Field field = instance.getClass().getDeclaredField(propertyName);
            field.setAccessible(true);
            //如果这个field是String，Integer，Boolean，Double等等，那么可以直接赋值
            //如果这个field是UserBasic、OrderBean、Book等自定义类型，那么不可以直接赋值，而是要new出实例对象，然后再赋值

            //因此，判断field的类型
            String filedTypeName = field.getType().getName();
            if(propertyValue!=null){
                if(isMyType(filedTypeName)){
                    //假设当前的field是UserBasic
                    Class fieldClass = field.getType();
                    ///获取带有Integer类型参数的构造方法，因为准备要通过反射调用这个构造方法来创建实例对象
                    Constructor constructor=fieldClass.getDeclaredConstructor(Integer.class);
                    propertyValue = constructor.newInstance(propertyValue);
                }
                field.set(instance,propertyValue);}
        } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException e){
            e.printStackTrace();
        }catch (InvocationTargetException e){
            e.printStackTrace();
        }catch (InstantiationException e){
            e.printStackTrace();
        }
    }

    //判断是否是自定义类型
    public static boolean isMyType(String className){
        switch (className){
            case "java.lang.String":
            case "java.lang.Integer":
            case "java.lang.Long":
            case "java.util.Date":
            case "java.sql.Date":
            case "java.lang.Double":
                return false;
            default:
                return true;
        }
    }
}