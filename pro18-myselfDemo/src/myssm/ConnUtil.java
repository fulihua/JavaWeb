package myssm;

import activitycommit.pojo.User;
import com.alibaba.druid.pool.DruidDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author fulihua
 * @date 2026/10/11 9:23 PM
 * @description
 */
public class ConnUtil {
    private static DruidDataSource dataSource;
    private static String DRIVER;
    private static String URL;
    private static String USER;
    private static String PWD;

    private static void initDataSource(){
        dataSource = new DruidDataSource();
        dataSource.setDriverClassName(DRIVER);
        dataSource.setUrl(URL);
        dataSource.setUsername(USER);
        dataSource.setPassword(PWD);
        dataSource.setTestOnBorrow(true);
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setValidationQueryTimeout(2000);
    }
    private static void loadProperties(){
        Properties properties = new Properties();
        InputStream inputStream = ConnUtil.class.getClassLoader().getResourceAsStream("druid.properties");
        try{
            properties.load(inputStream);
            DRIVER=properties.getProperty("driver");
            URL=properties.getProperty("url");
            USER =properties.getProperty("user");
            PWD=properties.getProperty("password");
            inputStream.close();
        }catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
    static{
        loadProperties();
        initDataSource();
    }


    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }

    public static DruidDataSource getDataSource(){
        return dataSource;
    }


    public static void close(Connection conn){
        if(conn!=null){
           try {
               conn.close();
            }catch (SQLException e){
               e.printStackTrace();
               throw new RuntimeException(e.getMessage());
           }
        }

    }
}