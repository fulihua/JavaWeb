package fruit.servlet;

import fruit.dao.FruitDao;
import fruit.dao.impl.FruitDaoImpl;
import fruit.servlet.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.server.ServerCloneException;

/**
 * @author fulihua
 * @date 2026/9/30 11:03 AM
 * @description
 */
public class DelServlet extends ViewBaseServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        //1.获取id
        Integer id = Integer.parseInt(request.getParameter("id"));
        //2.调用Dao中的删除方法
        FruitDao fruitDao = new FruitDaoImpl();
        fruitDao.delFruit(id);
        //3.重定向
        response.sendRedirect("/index");
    }
}