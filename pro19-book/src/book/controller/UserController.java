package book.controller;

import book.pojo.User;
import book.service.CartItemService;
import book.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

/**
 * @author fulihua
 * @date 2026/10/19 1:12 PM
 * @description
 */
public class UserController {

    private CartItemService cartItemService;

    private static final String LOGIN_FAIL_FAGE = "user/login";
    private static final String TO_INDEX_PAGE="redirect:book.do";
    private UserService userService;
    public String login(HttpServletRequest request, HttpSession session){
        String uname = request.getParameter("uname");
        String pwd = request.getParameter("pwd");
        System.out.println("【login】uname="+uname+" | pwd="+pwd);
        User currUser = userService.getUserByUnameAndPwd(uname,pwd);
        System.out.println("【login】查询结果currUser="+currUser);
        if(currUser==null) {
            System.out.println("【login】用户名密码错误，返回登录页");
            return LOGIN_FAIL_FAGE;
        }else{
            System.out.println("【login】登录成功！准备重定向book.do");
            cartItemService.getCartInfo(currUser);
            session.setAttribute("currUser",currUser);
            return TO_INDEX_PAGE;
        }
    }

    public String logOut(HttpSession session, HttpServletResponse response) throws IOException {
        //session默认有效时长是30分钟
        //session.setMaxInactiveInterval(60*5);//设置session的有效时长是5分钟
        session.invalidate();//强制让当前session失效

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.println("<script>alert('注销成功！');window.location.href='book.do';</script>");
        out.flush();
        return null;
    }
    public String regist(String uname,String pwd,String email,String yourCode,HttpSession session,HttpServletResponse response) throws IOException{
        String code = (String) session.getAttribute(KAPTCHA_SESSION_KEY);
        if (yourCode == code) {
            User user = new User(uname, pwd, email);
            userService.addUser(user);
            return TO_INDEX_PAGE;
        } else {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script>alert('验证码不对！');window.location.href='page?page=user/regist';</script>");
            out.flush();
            return null;
        }
    }
}