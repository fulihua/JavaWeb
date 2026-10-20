package myssm.util;

import myssm.ioc.BeanFactory;

import javax.servlet.ServletContext;

/**
 * @author fulihua
 * @date 2026/10/4 5:54 PM
 * @description
 */
public class IOCUtil {
    private static final String IOC_CONTAINER_KEY = "IOC_CONTAINER_KEY";
    public static BeanFactory getBeanFactory(ServletContext servletContext){
        return (BeanFactory) servletContext.getAttribute(IOC_CONTAINER_KEY);
    }

}