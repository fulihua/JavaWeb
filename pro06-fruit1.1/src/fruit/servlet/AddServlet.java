package fruit.servlet;

import fruit.dao.FruitDao;
import fruit.dao.impl.FruitDaoImpl;
import fruit.pojo.Fruit;
import fruit.servlet.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author fulihua
 * @date 2026/9/30 11:23 AM
 * @description
 */
@WebServlet("/add.do")
public class AddServlet extends ViewBaseServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码
        request.setCharacterEncoding("UTF-8");
        //2.获取参数
        String fname = request.getParameter("fname");
        Integer price = Integer.parseInt(request.getParameter("price"));
        Integer fcount = Integer.parseInt(request.getParameter("fcount"));
        String remark = request.getParameter("remark");
        //3.调用dao的添加方法
        Fruit fruit = new Fruit(fname,price,fcount,remark);
        FruitDao fruitDao = new FruitDaoImpl();
        fruitDao.addFruit(fruit);
        //4.重定向
        response.sendRedirect("/index");

    }
}