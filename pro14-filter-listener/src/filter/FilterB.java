package filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author fulihua
 * @date 2026/10/4 2:41 PM
 * @description
 */
public class FilterB implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //FilterConfig和ServletConfig类似，可以读取初始化信息
        System.out.println("FilterB正在初始化...");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //chain:链条
        System.out.println("FilterB拦截了请求...");
        chain.doFilter(request,response);//放行
        System.out.println("FilterB拦截了响应...");
    }

    @Override
    public void destroy() {
        System.out.println("FilterB正在销毁...");
    }
}