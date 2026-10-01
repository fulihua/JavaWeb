package fruit.servlet;

import fruit.dao.FruitDao;
import fruit.dao.impl.FruitDaoImpl;
import fruit.pojo.Fruit;
import fruit.servlet.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author fulihua
 * @date 2026/9/29 5:42 PM
 * @description
 */
public class EditServlet extends ViewBaseServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String idStr = request.getParameter("id");
        if(idStr!=null && !"".equals(idStr)){
            Integer id = Integer.parseInt(idStr);
            FruitDao fruitDao = new FruitDaoImpl();//多态写法的好处：解耦。以后如果换一个 FruitDao 的新实现类（比如 FruitDaoImpl2），**只需要改 new 后面这一处**，后面所有代码不用动。
            Fruit fruit = fruitDao.getFruitById(id);
            //将fruit保存到request作用域
            request.setAttribute("fruit",fruit);
        }
        super.processTemplate("edit",request,response);
    }
}