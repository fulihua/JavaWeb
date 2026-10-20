package myssm.listener;

import myssm.ioc.BeanFactory;
import myssm.ioc.ClassPathXmlApplicationContext;
import myssm.util.StringUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author fulihua
 * @date 2026/10/4 8:50 PM
 * @description
 */
public class ContextLoaderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        BeanFactory beanFactory = null;
        ServletContext cxt = sce.getServletContext();
        String contextConfigLocation = cxt.getInitParameter("contextConfigLocation");
        //创建IOC容器
        if(StringUtil.isNotEmpty(contextConfigLocation)){
            beanFactory = new ClassPathXmlApplicationContext(contextConfigLocation);
        }else{
            beanFactory = new ClassPathXmlApplicationContext();
        }
        cxt.setAttribute("IOC_CONTAINER_KEY",beanFactory);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}