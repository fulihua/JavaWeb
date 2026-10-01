package servlet;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @author fulihua
 * @date 2026/10/1 7:11 PM
 * @description
 */

//演示Servlet生命周期
//loadOnStartup用来设置Servlet的启动时机，默认值是-1，表示第一次访问时才会被实例化初始化
//loadOnStartup的取值是正整数，数字越小越在前面启动
//@WebServlet(urlPatterns = "/hello01",loadOnStartup=1)
public class Hello01Servlet extends GenericServlet {
    public Hello01Servlet(){
        System.out.println("Hello01Servlet正在实例化...");
    }
    @Override
    public void init() throws ServletException{
        System.out.println("Hello01Servlet正在初始化...");
    }
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("Hello01Servlet正在服务...");
    }
    @Override
    public void destroy(){
        System.out.println("Hello01Servlet正在销毁...");
    }
}