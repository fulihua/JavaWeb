package servlet;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author fulihua
 * @date 2026/10/1 8:04 PM
 * @description
 */

//演示Servlet的初始化
    /*
    @WebServlet(urlPatterns = "/hello02",
    initParams={
        @WebInitParam(name="uname",value="lina"),
        @WebInitParam(name="count",value="100")
        })
     */
public class Hello02Servlet extends GenericServlet {
    //初始化方法可以用来读取一些初始化的参数
    @Override
    public void init() throws ServletException{
        //ServletConfig表示当前Servlet的配置信息
        ServletConfig config = getServletConfig();
        String uname = config.getInitParameter("uname");
        System.out.println("uname="+uname);
        String count = config.getInitParameter("count");
        System.out.println("count="+count);
    }
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException{
        System.out.println("Hello02Servlet正在服务...");
    }

}