package fruit.servlet;

import fruit.dao.FruitDao;
import fruit.dao.impl.FruitDaoImpl;
import fruit.pojo.Fruit;
import fruit.servlet.ViewBaseServlet;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author fulihua
 * @date 2026/9/29 7:25 PM
 * @description
 */
@WebServlet("/update.do")
public class UpdateServlet extends ViewBaseServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
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

        //2.调用dao中的修改方法
        Fruit fruit = new Fruit(id,price,fcount,remark);
        FruitDao fruitDao = new FruitDaoImpl();
        fruitDao.updateFruit(fruit);

        //3.转发到index.html页面
        //processTemplate("index",request,response);//不能使用这一句，因为这样的话，index.html页面上获取的还是session中的老数据
        //4.因此我们需要重定向，让客户端重新给IndexServlet发请求，也就是重新查询数据，然后覆盖到session中
        response.sendRedirect("/index");

    }
}

/*

# 1、重定向 `response.sendRedirect("index.do")` 是什么？

**重定向是【服务器告诉浏览器：你去访问另一个地址】**
完整流程：

1. 浏览器提交表单，访问 `update.do`（进入 UpdateServlet 执行修改数据库）
2. `response.sendRedirect("index.do")` 给浏览器返回一个 302 响应
3. **浏览器收到响应，自动发起一次全新的 HTTP 请求：访问 `index.do`**
4. `index.do` 进入 IndexServlet，**重新执行查询数据库，把最新水果列表放入 session**
5. 再渲染 index 页面，页面就是修改之后最新的数据

>
> 重点：**两次请求！浏览器地址栏会变成 index.do**

老师注释里说的原因：
如果直接写 `processTemplate("index",request,response)`，这是**服务器内部转发**。

- 转发：只在服务器内部跳转，**浏览器只发 1 次请求**
- 不会重新执行 IndexServlet，不会重新查数据库，session 里面存的还是修改之前旧的 fruitList，页面数据不更新。
 */

/*

# 三种方式精简提炼

>
> 场景：UpdateServlet 修改完数据库之后

## 1. `super.processTemplate("index",request,response)`

**作用：直接渲染 Thymeleaf 模板**

- 不会调用 IndexServlet，**不会重新查数据库**
- 一次请求
- 地址栏不变
- ❌ 结果：页面显示旧数据，**修改后不能用**

## 2. `request.getRequestDispatcher("index.do").forward(request,response)`

**作用：请求转发（Servlet 原生）**

- 交给 IndexServlet 执行，**会重新查数据库，拿到新数据**
- 一次请求
- 地址栏不变（仍然是`update.do`）
- ⚠️ 缺点：按 F5 会重复提交表单；**功能可行，业务不推荐**

## 3. `response.sendRedirect("index.do")`

**作用：重定向**

- 浏览器发起全新请求，访问 IndexServlet，**重新查库拿到新数据**
- 两次请求
- 地址栏变成`index.do`
- ✅ 优点：F5 不会重复提交；**增删改首选方案**

# 极简口诀

1. `processTemplate`：渲染模板，**不跑其他 Servlet**
2. `forward`：服务器内部转交请求，**跑目标 Servlet**，一次请求，F5 重复提交风险
3. `sendRedirect`：让浏览器重新访问，**跑目标 Servlet**，两次请求，无重复提交风险

# 一句话速记

改完数据库：

- 直接渲染模板 → 旧数据 ❌
- 转发 forward → 新数据，但 F5 会重复提交 ⚠️
- 重定向 redirect → 新数据，安全 ✅

 */