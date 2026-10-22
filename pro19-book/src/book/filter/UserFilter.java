package book.filter;

import book.pojo.Cart;
import book.pojo.CartItem;
import book.pojo.User;
import myssm.util.StringUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fulihua
 * @date 2026/10/19 7:10 PM
 * @description
 */
public class UserFilter implements Filter {
    private List<String> baiList;
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        String uri = request.getRequestURI();
        String oper = request.getParameter("oper");
        oper = StringUtil.isNotEmpty(oper)?oper:"index";
        String accessURL = uri+"_"+oper;
        if(baiList.contains(accessURL)){
            filterChain.doFilter(request,response);
            return;
        }else {
            HttpSession session = request.getSession(false);
            Object userObj = null;
            if(session!=null){
                userObj = session.getAttribute("currUser");
            }
            if(userObj==null){
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html;charset=utf-8");
                PrintWriter out = response.getWriter();
                out.print("<script>alert('请先登录！');window.location.href='page?page=user/login';</script>");
                out.flush();
                return;
            }else{
                System.out.println("【UserFilter放行】sessionId="+session.getId());
                System.out.println("【UserFilter放行】currUser="+session.getAttribute("currUser"));
                filterChain.doFilter(request,response);
            }
        }
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        String bai = config.getInitParameter("bai");
        if(StringUtil.isNotEmpty(bai)){
            String[] Arr = bai.split(",");
            baiList= Arrays.asList(Arr);
        }
    }

    @Override
    public void destroy() {
    }
}