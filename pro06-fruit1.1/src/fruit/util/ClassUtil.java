//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package fruit.util;

import java.lang.reflect.Field;

public class ClassUtil {
    public ClassUtil() {
    }

    public static Class getClass(String entityName) {
        try {
            return Class.forName(entityName);
        } catch (ClassNotFoundException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static Object createInstance(String entityName) {
        try {
            return getClass(entityName).newInstance();
        } catch (InstantiationException var2) {
            var2.printStackTrace();
        } catch (IllegalAccessException var3) {
            var3.printStackTrace();
        }

        return null;
    }

    public static void setProperty(Object instance, String propertyName, Object propertyValue) {
        try {
            Field field = instance.getClass().getDeclaredField(propertyName);
            field.setAccessible(true);
            field.set(instance, propertyValue);
        } catch (IllegalAccessException | NoSuchFieldException var4) {
            var4.printStackTrace();
        }

    }
}
