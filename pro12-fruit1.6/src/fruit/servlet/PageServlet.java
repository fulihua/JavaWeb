package fruit.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author fulihua
 * @date 2026/10/1 2:13 PM
 * @description
 */
@WebServlet("/page")
public class PageServlet extends ViewBaseServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String page = request.getParameter("page");
        processTemplate(page,request,response);
    }
}