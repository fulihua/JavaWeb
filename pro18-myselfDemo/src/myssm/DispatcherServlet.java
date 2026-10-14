package myssm;

import com.alibaba.fastjson2.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fulihua
 * @date 2026/10/12 10:46 AM
 * @description
 */

public class DispatcherServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        //获取oper，确定执行哪个方法
        String oper = req.getParameter("oper");
        if(oper==null||oper.trim().isEmpty()){
            resp.getWriter().write("oper参数缺失");
            return;
        }
        Map<String,Object> resultMap = new HashMap<>();
        HttpSession session = req.getSession();

        try{
            Object controllerObj = null;
// 解析请求URL路径，用来匹配控制器
            String path = req.getServletPath();
            System.out.println("===== 当前servletPath = "+path);
            if(path.endsWith("/user.do")){
                controllerObj=new activitycommit.controller.UserController();
            }else if(path.endsWith("/activity.do")){
                controllerObj = new activitycommit.controller.ActivityController();
            }else if(path.endsWith("/signup.do")){
                controllerObj = new activitycommit.controller.SignUpController();
            }

// 如果路径匹配不到controller，直接返回404
            if(controllerObj == null){
                resultMap.put("code",404);
                resultMap.put("msg","找不到控制器");
                resp.setContentType("application/json;charset=utf-8");
                PrintWriter out = resp.getWriter();
                out.write(JSON.toJSONString(resultMap));
                return;
            }

            Method method = controllerObj.getClass().getDeclaredMethod(oper,Integer.class,Integer.class,HttpSession.class,HttpServletRequest.class,Map.class);
            Integer userId = null;
            String userIdStr = req.getParameter("userId");
            if(userIdStr!=null&&!userIdStr.isEmpty()){
                userId = Integer.parseInt(userIdStr);
            }
            Integer activityId = null;
            String activityIdStr = req.getParameter("activityId");
            if(activityIdStr!=null&&!activityIdStr.isEmpty()) {
               activityId= Integer.parseInt(activityIdStr);
            }
            String returnStr = (String)method.invoke(controllerObj,userId,activityId,session,req,resultMap);
            if("json".equals(returnStr)){
                resp.setContentType("application/json;charset=utf-8");
                PrintWriter out = resp.getWriter();
                String json = JSON.toJSONString(resultMap);
                out.write(json);
                out.flush();
                out.close();
            }else if(returnStr.startsWith("redirect:")){
                // 原来的重定向逻辑（和你QQZone保持不变）
                 path = returnStr.substring("redirect:".length());
                resp.sendRedirect(path);
            }else if(returnStr.startsWith("forward:")){
                // 原来的请求转发逻辑
                 path = returnStr.substring("forward:".length());
                req.getRequestDispatcher(path).forward(req,resp);
                }
            }catch (Exception e){
            e.printStackTrace();
            resultMap.put("code",500);
            resultMap.put("msg","服务器内部异常");
            // 异常也返回json
            resp.setContentType("application/json;charset=utf-8");
            PrintWriter out = resp.getWriter();
            out.write(JSON.toJSONString(resultMap));
             }
        }
}