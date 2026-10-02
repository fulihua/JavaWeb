package fruit.servlet;

import fruit.pojo.Fruit;
import fruit.service.FruitService;
import fruit.service.impl.FruitServiceImpl;
import fruit.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author fulihua
 * @date 2026/10/1 3:46 PM
 * @description
 */
@WebServlet("/fruit.do")
public class FruitServlet extends ViewBaseServlet {
    private static final String FRUIT_DO = "fruit.do";
    private FruitService fruitService = new FruitServiceImpl();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("UTF-8");

        //从请求参数中获取一个叫oper的值。这个值和将来要执行的方法名对应
        //如果oper=index,那么我们就调用index方法
        //如果oper=update,那么我们就调用update方法
        String oper = request.getParameter("oper");
        if (StringUtil.isNotEmpty(oper)) {
            oper = "index";
        }
        try {
            Method method = this.getClass().getDeclaredMethod(oper, HttpServletRequest.class, HttpServletResponse.class);
            /*
            - `this.getClass()`：拿着房子，反过去找到**造它的那张原始图纸**。
            - `getDeclaredMethod(方法名, 参数类型列表)`：**只拿【指定名字、指定参数列表】那**一个**方法**。
            -Java 找重载方法的时候，是靠【参数类型】区分，不是传参数值！怎么描述 “参数类型”？就是用 `类名.class`。
           注意： 这里**传的不是 request、response 对象本身！**
            不是传请求里面的数据，只是告诉反射：**我要找的方法，参数是什么类型**。
             */
            method.invoke(this, request, response);
            //invoke = **执行这个方法**. invoke 里面的 request、response 才是**真正传给方法运行的实际对象**。
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("找不到" + oper + "对应的方法或者执行" + oper + "对应付方法出错！");
        }

        /*

        - 有对象 → `对象.getClass()`
        - 只有类名字符串、没有对象 → `Class.forName("全类名")`
        两者结果都是 Class（图纸），只是**获取图纸的途径不一样**。
         */
    }

    public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        if (StringUtil.isNotEmpty(operate) && "search".equals(operate)) {
            String keywordStr = request.getParameter("keyword");
            if (StringUtil.isNotEmpty(keywordStr)) {
                keyword = keywordStr;
            }
            session.setAttribute("keyword", keyword);
        } else {
            Object keywordObj = session.getAttribute("keyword");
            if (keywordObj != null) {
                keyword = (String) keywordObj;
            }
        }
        Integer pageNo = 1;
        String pageNoStr = request.getParameter("pageNo");
        if (pageNoStr != null && !"".equals(pageNoStr)) {
            pageNo = Integer.parseInt(pageNoStr);
        }
        Integer pageCount = fruitService.getPageCount(keyword);
       //将总页数保存到session中
        session.setAttribute("pageCount", pageCount);
        //如果pageNo是负数，则赋值为1；如果pageNo超过了总页数，则赋值总页数
        if (pageNo <= 0) {
            pageNo = 1;
        }
        if (pageNo > pageCount) {
            pageNo = pageCount;
        }
        session.setAttribute("pageNo", pageNo);
        //调用查询方法
        List<Fruit> fruitList = fruitService.getFruitList(keyword, pageNo);
        //`request.getSession()`：拿到当前用户的 session 对象（会话）
        session.setAttribute("fruitList", fruitList);//把查到的水果列表**存进 session 域**
        // - 第一个参数`"fruitList"`：**key 名字**，thymeleaf 页面用 `${session.fruitList}` 就是读取这个 key
        // - 第二个参数：要存的数据（水果集合）
        //转发到首页（当然，转发之前，thymeleaf会帮我们做视图渲染）
        super.processTemplate("index", request, response);
        /*
        super` 就是父类 `ViewBaseServlet` 的方法，**这是Thymeleaf封装好的视图渲染方法**
- `"index"`：视图名字，会自动拼接前缀后缀，找到 `index.html`
         */

    }


    protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码
        request.setCharacterEncoding("UTF-8");
        //2.获取参数
        String fname = request.getParameter("fname");
        Integer price = Integer.parseInt(request.getParameter("price"));
        Integer fcount = Integer.parseInt(request.getParameter("fcount"));
        String remark = request.getParameter("remark");
        //3.调用dao的添加方法
        Fruit fruit = new Fruit(fname, price, fcount, remark);
        fruitService.addFruit(fruit);
        //4.重定向
        response.sendRedirect(FRUIT_DO);

    }

    protected void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取id
        Integer id = Integer.parseInt(request.getParameter("id"));
        //2.调用Dao中的删除方法
       fruitService.delFruit(id);
        //3.重定向
        response.sendRedirect(FRUIT_DO);
    }

    protected void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null && !"".equals(idStr)) {
            Integer id = Integer.parseInt(idStr);
            Fruit fruit = fruitService.getFruitById(id);
            //将fruit保存到request作用域
            request.setAttribute("fruit", fruit);
        }
        super.processTemplate("edit", request, response);
    }

    protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //0.设置请求时编码(只要是针对POST请求)(从Tomcat8开始，tomcat内部的编码从ISO-8859-1变成了UTF-8，因此GET请求不再需要设置编码)
        request.setCharacterEncoding("UTF-8");//这句话一定要在使用request之前设置

        //1.获取请求参数
        Integer price = Integer.parseInt(request.getParameter("price"));
        Integer fcount = Integer.parseInt(request.getParameter("fcount"));//// 拿到字符串"10"，必须转成Integer类型，才能存进Fruit对象的fcount属性（Integer）
        // remark字段本身就是字符串类型，拿到直接用，不需要转换
        String remark = request.getParameter("remark");

        //之前的GET方式的编码处理（了解）
        // remark = new String(remark.getBytes("ISO-8859-1"),"UTF-8");

        Integer id = Integer.parseInt(request.getParameter("id"));

        Fruit fruit = new Fruit(id, price, fcount, remark);
        fruitService.updateFruit(fruit);

        //3.转发到index.html页面
        //processTemplate("index",request,response);//不能使用这一句，因为这样的话，index.html页面上获取的还是session中的老数据
        //4.因此我们需要重定向，让客户端重新给IndexServlet发请求，也就是重新查询数据，然后覆盖到session中
        response.sendRedirect(FRUIT_DO);
    }
}