package listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author fulihua
 * @date 2026/10/4 3:02 PM
 * @description
 */
//@WebListener
//ServletContextListener:是用来监听ServletContext对象创建和销毁的监听器
public class MyServletContextListener implements ServletContextListener {
    //当ServletContext对象被创建的时候，此方法自动执行
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent ){
        System.out.println("ServletContext对象被创建...");
        //获取ServletContext对象
        System.out.println(servletContextEvent.getServletContext());
        ServletContext cxt = servletContextEvent.getServletContext();
        String hello = cxt.getInitParameter("hello");
        System.out.println("hello=+"+hello);
    }
    //但ServletContext对象被摧毁的时候，此方法自动执行
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent){
        System.out.println("ServletContext对象被销毁...");
    }
}