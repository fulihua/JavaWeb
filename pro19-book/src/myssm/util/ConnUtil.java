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
        dataSource.setTestOnBorrow(true);
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setValidationQueryTimeout(2000);
    }

    private static void loadProperties() {
        InputStream inputStream = ConnUtil.class.getClassLoader().getResourceAsStream("jdbc.properties");//现在的代码是**静态代码块 static {}**，
        // 静态代码块执行的时候，**不存在 ConnUtil 的实例对象，没有 this**，所以**不能写`this.getClass().getClassLoader()`，只能用`ConnUtil.class.getClassLoader()`**。
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

    public static Connection getConn() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void closeConn(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }
    }

}