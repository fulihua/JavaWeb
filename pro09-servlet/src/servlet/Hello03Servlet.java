package servlet;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author fulihua
 * @date 2026/10/1 8:18 PM
 * @description
 */
public class Hello03Servlet extends GenericServlet {
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {

        /*
       获取ServletContext有如下几种方式：
       1.通过request对象可以获取
       ServletContext context = ServletRequest.getServletContext();
       2.通过session对象可以获取
       HttpSession session = ((HttpServletRequest)servletRequest).getSession();
       ServletContext context = session.getServletContext();
       3.直接调用父类中的getServletContext()方法
       ServletContext context = getServletContext();
       4.通过ServletConfig对象也可以获取
         */
        ServletConfig config = getServletConfig();
        ServletContext context = config.getServletContext();

        String hello = context.getInitParameter("hello");
        System.out.println("hello="+hello);
        System.out.println("Hello03Servlet正在服务...");
    }
}