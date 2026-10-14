package myssm;

import javax.servlet.FilterChain;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


/**
 * @author fulihua
 * @date 2026/10/12 8:44 AM
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
        }catch (InstantiationException e){
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }
        return null;
    }

    public static void setSimpleProperty(Object instance,String propetyName,Object propertyValue){
        try{
            Field field = instance.getClass().getDeclaredField(propetyName);
            field.setAccessible(true);
            if(propertyValue!=null){
            field.set(instance,propertyValue);}
        }catch (NoSuchFieldException | IllegalAccessException e){
            e.printStackTrace();
        }
    }

    public static void setProperty(Object instance,String propetyName,Object properyValue){
        try{
            Field field = instance.getClass().getDeclaredField(propetyName);
            field.setAccessible(true);
            String fieldTypeName = field.getType().getName();
            if(properyValue!=null) {
                if (isMyType(fieldTypeName)) {
                    Class fieldClass = field.getType();
                    Constructor constructor = fieldClass.getDeclaredConstructor(Integer.class);
                    properyValue = constructor.newInstance(properyValue);
                }
                if(properyValue instanceof LocalDateTime){
                    LocalDateTime ldt = (LocalDateTime) properyValue;
                    properyValue = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
                }
            }
            field.set(instance,properyValue);
        }catch (NoSuchFieldException e){
            e.printStackTrace();
        }catch (NoSuchMethodException e){
            e.printStackTrace();
        }catch (InstantiationException  | IllegalAccessException | InvocationTargetException e){
            e.printStackTrace();
        }
    }

    public static boolean isMyType(String fieldTypeName){
        switch (fieldTypeName){
            case "java.lang.Integer":
            case "java.lang.String":
            case "java.lang.Long":
            case "java.util.Date":
            case "java.sql.Date":
                return false;
            default:
                return true;
        }

    }

    public static String underLineToCamel(String columnName){
        if(columnName == null || !columnName.contains("_")){
            return columnName;
        }
        StringBuilder sb = new StringBuilder();
        boolean nextUpper = false;
        for(char c:columnName.toCharArray()){
            if(c == '_'){
                nextUpper = true;
            }else{
                    if(nextUpper){
                        sb.append(Character.toUpperCase(c));
                        nextUpper = false;
                    }else {
                        sb.append(c);
                    }
            }
        }
        return sb.toString();
    }

}