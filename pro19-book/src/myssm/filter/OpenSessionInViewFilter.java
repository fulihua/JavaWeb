package myssm.filter;

import myssm.trans.TransactionManager;

import javax.servlet.*;
import java.io.IOException;


/**
 * @author fulihua
 * @date 2026/10/4 5:47 PM
 * @description
 */
public class OpenSessionInViewFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig)throws ServletException {}
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)throws IOException,ServletException{
        try{
            //开启事务
            TransactionManager.begin();
            chain.doFilter(request,response);
            //提交事务
            TransactionManager.commit();
        }catch (Exception e){
            e.printStackTrace();
            //回滚事务
            TransactionManager.rollback();
        }
    }
    @Override
    public void destroy(){}
}