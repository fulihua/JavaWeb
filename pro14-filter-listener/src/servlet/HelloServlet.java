package servlet;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * @author fulihua
 * @date 2026/10/4 2:42 PM
 * @description
 */
@WebServlet("/hello")
public class HelloServlet extends GenericServlet {
    @Override
    public void init() throws ServletException{
        System.out.println("HelloServlet正在初始化...");
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("HelloServlet正在服务...");
    }


    public void destory(){
        System.out.println("HelloServlet正在销毁...");
    }

}