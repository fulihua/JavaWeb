1.Filter的基础知识
1)Filter表示过滤器，属于Servlet规范
    它也有三个核心方法：init(),doFilter(),destroy()
2)Filter中的doFilter方法：
    public void doFilter(request,response,chain){
        //这里的代码是请求到来时执行
        chain.doFilter(request,response);//放行
        //这里的代码是响应到来时执行
    }
3) Filter的配置
    -第一种可以使用注解 @WebFilter，但是更推荐使用XML配置(因为过滤器链的执行顺序问题）
    -第二种可以使用XML配置
4) Filter和Servlet的初始化顺序问题
    -在Tomcat启动时，Filter就已经初始化了
    -而Servlet默认是要访问它时，才会去初始化
    -因此，这两者的顺序是：
      （1）Filter 实例化、初始化
      （2）Servlet 实例化、初始化
      （3）Filter的doFilter方法执行，然后放行
      （4）Servlet的服务方法执行
      （5）在Tomcat停止时，Servlet先销毁，然后再销毁Filter

2.Filter-过滤器

3.Filter的应用
1) CharacterEncodingFilter
2)OpenSessionInViewFilter

4.
1) ServletContextListener
    监视ServletContext对象的创建和销毁的过程
2) HttpSessionListener
    监视HttpSession对象的创建和销毁的过程
3) ServletRequestListener
    监视ServletRequest对象的创建和销毁的过程

4) ServletContextAttributeListener
    监视application作用域的值的变化
5) HttpSessionAttributeListener
    监视HttpSession作用域的值的变化
6) ServletRequestAttributeListener
    监视ServletRequest作用域的值的变化

7) HttpSessionBindingListener
    监视指定的对象向session报错或者session属性域移除
8) HttpSessionActivationListener
    监视指定的对象在session中序列化和反序列化的过程














