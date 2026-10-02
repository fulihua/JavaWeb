package fruit.controller;

import fruit.pojo.Fruit;
import fruit.service.FruitService;
import fruit.servlet.ViewBaseServlet;
import fruit.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author fulihua
 * @date 2026/10/1 3:46 PM
 * @description
 */

public class FruitController extends ViewBaseServlet {
    private static final String FRUIT_DO = "fruit.do";
    private FruitService fruitService;

    protected String index(HttpSession session,String operate,String keyword,Integer pageNo){
        if(StringUtil.isEmpty(keyword)){
            keyword="";
        }
        if (StringUtil.isNotEmpty(operate) && "search".equals(operate)) {

            session.setAttribute("keyword", keyword);
        } else {
            Object keywordObj = session.getAttribute("keyword");
            if (keywordObj != null) {
                keyword = (String) keywordObj;
            }
        }
        if(pageNo==null) {
            pageNo = 1;
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
       return "index";
        /*
        super` 就是父类 `ViewBaseServlet` 的方法，**这是Thymeleaf封装好的视图渲染方法**
- `"index"`：视图名字，会自动拼接前缀后缀，找到 `index.html`
         */

    }


    protected String add(String fname,Integer price,Integer fcount,String remark){
        Fruit fruit = new Fruit(fname, price, fcount, remark);
        fruitService.addFruit(fruit);
        //4.重定向
        return "redirect:"+FRUIT_DO;
    }

    protected String del( Integer id){
       fruitService.delFruit(id);
       return "redirect:"+FRUIT_DO;
    }

    protected String edit(HttpServletRequest request,Integer id) {
        if (id != null) {
            Fruit fruit = fruitService.getFruitById(id);
            request.setAttribute("fruit", fruit);
            return "edit";
        }
        return null;
    }

    protected String update(Integer id , Integer price , Integer fcount , String remark){
        Fruit fruit = new Fruit(id , price , fcount , remark) ;
        fruitService.updateFruit(fruit);
        return "redirect:"+FRUIT_DO;
    }
}