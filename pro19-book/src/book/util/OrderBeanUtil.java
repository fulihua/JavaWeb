package book.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author fulihua
 * @date 2026/10/19 1:47 PM
 * @description
 */
public class OrderBeanUtil {
    //SimpleDateFormat是一个日期格式化类
    //它有两个常用的方法：
    //String -> Date    sdf.parse(str)
    //Date -> String    sdf.format(date)
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    public static String generateOrderNoStr(Date date) {
        String orderDateStr = sdf.format(date);
        String uuidStr = UUID.randomUUID().toString().replaceAll("-","");
        return uuidStr+"_"+orderDateStr;
    }
}