package fruit.servlet;

import fruit.dao.FruitDao;
import fruit.dao.impl.FruitDaoImpl;
import fruit.pojo.Fruit;
import fruit.servlet.ViewBaseServlet;
import fruit.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author fulihua
 * @date 2026/9/28 7:21 PM
 * @description
 */
public class IndexServlet extends ViewBaseServlet {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        String keyword = "";
        //并不是每一次都能从请求参数中获取到keyword的
        //应该是：
        //点击了查询按钮，我们应该从请求参数中获取keyword，然后将其保存到session中
        //如果不是点击查询按钮，我们应该从session中获取keyword
        //那么如何判断是否点击了查询按钮呢？
        //我们在表单中添加了一个隐藏域，operate=search
        //如果我们从请求参数中获取operate的值，能获取到，则表示用户点击了查询按钮。如果获取不到，表示用户没有点击
        String operate = request.getParameter("operate");
        if(StringUtil.isNotEmpty(operate) && "search".equals(operate)){
            String keywordStr = request.getParameter("keyword");
            if(StringUtil.isNotEmpty(keywordStr)){
                keyword=keywordStr;
            }
            session.setAttribute("keyword",keyword);
            }else{
            Object keywordObj = session.getAttribute("keyword");
            if(keywordObj!=null){
                keyword=(String) keywordObj;
            }
        }
            Integer pageNo=1;
            String pageNoStr = request.getParameter("pageNo");
            if(pageNoStr!=null && !"".equals(pageNoStr)){
                pageNo=Integer.parseInt(pageNoStr);
            }
       //创建Dao实例
        FruitDao fruitDao = new FruitDaoImpl();
        //查询总记录条数
        Integer totalCount = fruitDao.getTotalCount(keyword);
        //计算总页数
        Integer pageCount = (totalCount+5-1)/5;
        //将总页数保存到session中
        session.setAttribute("pageCount",pageCount);
        //如果pageNo是负数，则赋值为1；如果pageNo超过了总页数，则赋值总页数
        if(pageNo<=0){
            pageNo=1;
        }
        if(pageNo>pageCount){
            pageNo=pageCount;
        }
        session.setAttribute("pageNo",pageNo);
        //调用查询方法
        List<Fruit> fruitList = fruitDao.getFruitList(keyword,pageNo);
        //`request.getSession()`：拿到当前用户的 session 对象（会话）
        session.setAttribute("fruitList",fruitList);//把查到的水果列表**存进 session 域**
        // - 第一个参数`"fruitList"`：**key 名字**，thymeleaf 页面用 `${session.fruitList}` 就是读取这个 key
        // - 第二个参数：要存的数据（水果集合）
        //转发到首页（当然，转发之前，thymeleaf会帮我们做视图渲染）
        super.processTemplate("index",request,response);
        /*
        super` 就是父类 `ViewBaseServlet` 的方法，**这是Thymeleaf封装好的视图渲染方法**
- `"index"`：视图名字，会自动拼接前缀后缀，找到 `index.html`
         */

    }
}