package myssm.util;

import com.alibaba.druid.pool.DruidDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author fulihua
 * @date 2026/10/4 5:24 PM
 * @description
 */
public class ConnUtil {
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    private static DruidDataSource dataSource;

    private static String DRIVER;
    private static String URL;
    private static String USER;
    private static String PWD;

    private static void initDataSource() {
        dataSource = new DruidDataSource();
        dataSource.setDriverClassName(DRIVER);
        dataSource.setUrl(URL);
        dataSource.setUsername(USER);
        dataSource.setPassword(PWD);
    }

    private static void loadProperties() {
        InputStream inputStream = ConnUtil.class.getClassLoader().getResourceAsStream("jdbc.properties");//`getClass()` 需要实体对象！而 `this` 就是那个对象！
        Properties properties = new Properties();
        try {
            properties.load(inputStream);

            DRIVER = properties.getProperty("driver");
            URL = properties.getProperty("url");
            USER = properties.getProperty("user");
            PWD = properties.getProperty("pwd");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

   static {
       loadProperties();
       initDataSource();
   }

   public static Connection getConn(){
        Connection connection = threadLocal.get();
        if(connection == null){
            try{
                connection = dataSource.getConnection();
                threadLocal.set(connection);

            }catch (SQLException e){
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }
        return connection;
   }

   public static void closeConn(){
        Connection connection = threadLocal.get();
        if(connection!=null){
            try{
                connection.close();
                threadLocal.set(null);
            }catch (SQLException e){
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}