package myssm;

/**
 * @author fulihua
 * @date 2026/10/13 9:20 AM
 * @description
 */

import com.alibaba.druid.pool.DruidDataSource;
import myssm.ConnUtil;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

    @WebListener
    public class ClosePoolListener implements ServletContextListener {
        @Override
        public void contextDestroyed(ServletContextEvent sce) {
            //关闭Druid连接池
            DruidDataSource dataSource = (DruidDataSource) ConnUtil.getDataSource();
            if(dataSource != null){
                dataSource.close();
            }
            //注销JDBC驱动
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()){
                Driver driver = drivers.nextElement();
                try {
                    DriverManager.deregisterDriver(driver);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        @Override
        public void contextInitialized(ServletContextEvent sce) {
            //项目启动，不用处理
        }
    }

