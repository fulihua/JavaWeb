package myssm;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author fulihua
 * @date 2026/10/12 9:48 AM
 * @description
 */
@WebFilter("/*")
public class OpenSessionInViewFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig)throws ServletException{}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,ServletException{
        try{
            TransManager.begin();
            chain.doFilter(request,response);
            TransManager.commit();
        }catch (Exception e){
            e.printStackTrace();
           TransManager.rollback();
        }
    }

    @Override
    public void destroy(){}
}