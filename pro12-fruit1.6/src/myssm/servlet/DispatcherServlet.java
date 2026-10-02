package myssm.servlet;

import fruit.servlet.ViewBaseServlet;
import fruit.util.StringUtil;
import myssm.ioc.BeanFactory;
import myssm.ioc.ClassPathXmlApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author fulihua
 * @date 2026/10/2 8:41 PM
 * @description
 */

@WebServlet("*.do")
public class DispatcherServlet extends ViewBaseServlet {
    private BeanFactory beanFactory = new ClassPathXmlApplicationContext();

    @Override
    protected  void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        //假设访问的URL是： http://localhost/fruit.do?id=9&fname=apple&price=10
        //String uri = request.getRequestURI();
        //System.out.println(uri);        //uri 是 /fruit.do
        //String queryString = request.getQueryString();//queryString 是   id=9&fname=apple&price=10

        //假设访问的是 /fruit.do
        //取出其中的字符串： fruit -> fruitController
        String uri = request.getRequestURI();
        uri = uri.substring(1); // fruit.do
        int lastIndexDotIndex = uri.lastIndexOf(".do");
        uri = uri.substring(0, lastIndexDotIndex);//fruit

        //根据fruit这个id从IOC容器中获取到对应的bean实例
        Object bean = beanFactory.getBean(uri);
        String oper = request.getParameter("oper");
        if(StringUtil.isEmpty(oper)){
            oper="index";
        }
        //下面准备通过反射技术调用bean实例中的oper对应的方法
        //获取到bean的Class对象
        Class beanClass = bean.getClass();
        //此处先获取所有的方法。然后通过循环根据方法名获取到指定的方法
        //不能再通过方法名加参数类型的方法获取了。因为将来的版本我们的controller中的每一个方法参数都不一样
        Method[] methods = beanClass.getDeclaredMethods();
        for(int i=0;i<methods.length;i++) {
            Method method = methods[i];
            if (oper.equals(method.getName())) {
                try {
                    //第2步：调用控制器方法
                    method.setAccessible(true);
                    method.invoke(bean, request, response);

                    //第3步：处理统一视图（转发或者重定向）
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("找不到" + oper + "对应的方法或者执行" + oper + "对应的方法出错！");
                }
            }
        }
    }
}