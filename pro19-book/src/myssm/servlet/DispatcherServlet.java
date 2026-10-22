package myssm.servlet;

import myssm.ioc.BeanFactory;
import myssm.util.IOCUtil;
import myssm.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;

/**
 * @author fulihua
 * @date 2026/10/2 8:41 PM
 * @description
 */

//@WebServlet("*.do")
public class DispatcherServlet extends ViewBaseServlet {
    private BeanFactory beanFactory;

    @Override
    public void init() throws ServletException{
        super.init();//这句话不能省略，因为父类的初始化方法中也有操作，需要创建模版引擎对象
        beanFactory = IOCUtil.getBeanFactory(getServletContext());
    }


    @Override
    protected  void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        //假设访问的URL是： http://localhost/fruit.do?id=9&fname=apple&price=10
        //String uri = request.getRequestURI();
        //System.out.println(uri);        //uri 是 /fruit.do
        //String queryString = request.getQueryString();//queryString 是   id=9&fname=apple&price=10

        //假设访问的是 /fruit.do
        //取出其中的字符串： fruit -> fruitController
        // 请求进来第一行就加这个打印
        System.out.println("====全部请求参数====");
        Map<String,String[]> map = request.getParameterMap();
        for(Map.Entry<String,String[]> entry : map.entrySet()){
            System.out.println(entry.getKey() + " = " + Arrays.toString(entry.getValue()));
        }
        System.out.println("==================");

        System.out.println("【Dispatcher】收到请求 uname=" + request.getParameter("uname"));

        String uri = request.getRequestURI();
        uri = uri.substring(1); // fruit.do
        int lastIndexDotIndex = uri.lastIndexOf(".do");
        uri = uri.substring(0, lastIndexDotIndex);//fruit

        //根据fruit这个id从IOC容器中获取到对应的bean实例
        Object bean = beanFactory.getBean(uri);
        String oper = request.getParameter("oper");
        if(StringUtil.isEmpty(oper)){
            oper="index";
            //这里的 `oper="index"`，**不是写死某一个特定 Controller 的 index 方法**；
            //是：**拿到当前 URL 匹配出来的那个 Controller Bean，去调用这个 Controller 自己内部的 index () 方法**。
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
                    method.setAccessible(true);
                    //第1步：统一对参数进行处理
                    //参数分为两种：① HttpServletRequest，HttpSession,HttpServletResponse等参数
                    //            ② fid,fname,price...这样的请求参数

                    //通过反射技术获取method中的参数签名
                    Parameter[] parameters = method.getParameters();
                    Object[] parameterValueArr = new Object[parameters.length];
                    for(int j=0;j<parameters.length;j++){
                        Parameter parameter = parameters[j];
                        //获取参数名称，例如：session , operate , keyword ....
                        //JDK中的反射技术获取形式参数的名称，默认获取到的是arg0,arg1,arg2.....
                        //也就是说，默认情况下，字节码文件(class文件)中是不包含形参名称信息的
                        //从JDK8开始，我们反射技术也可以获取形参名称（缺点是，字节码文件体积变大）
                        //但是，我们需要在java编译器上做一个设置：-parameters
                        //idea的菜单file->settings->builder->compiler->java compiler -> additional command line ...
                        //另外，还需要设置 file->other settings->new Project ....后面是一样的
                        Class<?> paramClass = parameter.getType();
                        //参数值
                        Object parameterValue = null ;
                        if(HttpServletRequest.class.isAssignableFrom(paramClass)) {
                            parameterValue=request;
                        }else if(HttpSession.class.isAssignableFrom(paramClass)) {
                            parameterValue=request.getSession();//`request.getSession()`等价于`request.getSession(true)`！！！找不到就新建！
                            //session是Dispatcher注入进来的，底层执行过 request.getSession(true)，有新建风险,新建了之后和我们在页面上拿到的就不是同一个session了
                        }else if(HttpServletResponse.class.isAssignableFrom(paramClass)) {
                            parameterValue=response;
                        }else{
                                String parameterName = parameter.getName();
                                String parameterValueStr = request.getParameter(parameterName);
                                //此处我们不考虑一个名称对应多个值的情况
                                //http://localhost/fruit.do?hobby=football&hobby=basketball&hobby=pingpong
                                //String[] hobbies = request.getParameterValues("hobby");

                                if(StringUtil.isNotEmpty(parameterValueStr)){
                                    switch (paramClass.getName()){
                                        case "java.lang.Integer":
                                            parameterValue = Integer.parseInt(parameterValueStr) ;
                                            break;
                                        case "java.lang.Double":
                                            parameterValue = Double.parseDouble(parameterValueStr) ;
                                            break;
                                        default:
                                            parameterValue=parameterValueStr;
                                            break;
                                    }
                                }

                        }

                        parameterValueArr[j] = parameterValue;
                    }

                    //第2步：调用控制器方法
                    Object returnObj = method.invoke(bean, parameterValueArr);

                    //第3步：处理统一视图（转发或者重定向）
                    if(returnObj!=null){
                        String returnStr = (String)returnObj;
                        if(returnStr.startsWith("redirect")){
                            returnStr = returnStr.substring("redirect:".length());
                            response.sendRedirect(returnStr);
                        }else {
                            processTemplate(returnStr,request,response);
                        }
                    }
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendRedirect("book.do");
                    return;                }
            }
        }
    }
}