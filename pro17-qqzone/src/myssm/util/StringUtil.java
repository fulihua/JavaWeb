package myssm.util;

/**
 * @author fulihua
 * @date 2026/9/30 2:34 PM
 * @description
 */
public class StringUtil {
    public static boolean isEmpty(String str){
        return str==null || "".equals(str);
    }
    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }
}
