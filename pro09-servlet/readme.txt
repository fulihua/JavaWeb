1.Servlet的继承关系
1)javax.servlet.Servlet接口
2)javax.servlet.GenericServlet抽象子类
3)javax.servlet.http.HttpServlet这个抽象类是GenericServlet抽象子类

2.Servlet接口的源码：
public interface Servlet{
    //1.初始化方法
    void init(ServletConfig config);
    //2.服务方法
    void service(ServletRequest request,ServletResponse response);
    //3.销毁方法
    void destroy();
}

3.GenericServlet的源码：
public abstract class GenericServlet implements Servlet{
    public void destroy(){
    }
    public void init(ServletConfig config){
    this.config = config;
    this.init();//此处又调用了自己的无参init方法
    }

    //这个无参init方法内部什么都没写，那么将来如果我们有一些初始化的工作需要做，可以重写这个初始化方法
    public void init() throws ServletException{
    }

    //服务方法仍然保持抽象
    public abstract void service(ServletRequest request,ServletResponse response);
}

4.Servlet的生命周期
1)Servlet生命周期经过4个阶段：实例化、初始化、服务、销毁
2)实例化 其实就是调用 无参构造方法
3)初始化 其实就是调用init方法
4)服务 其实就是调用service方法
5)销毁 其实就是调用destroy方法
6)代码结论：
    -Servlet默认情况下tomcat在启动的时候，它不会实例化初始化
    -当第一次给Hello01Servlet发请求时，它会实例化、初始化、服务。以后每次访问都是直接服务
    -当tomcat服务器停止时，Hello01Servlet的销毁方法会被执行

5.Servlet的启动时机
1)默认情况下，每一个Servlet都是在第一次被请求时，容器才会实例化初始化它
2)我们可以通过<load-on-startup>来设置servlet的启动时机

6.Servlet的初始化和init方法
1)在GenericServlet的源码中，我们发现有两个init方法
那么，如果我们需要做一些初始化的工作，我们最好重写无参的init
2)ServletConfig表示当前Servlet的配置信息
config.getInitParameter(key)表示获取初始化参数
3)注解方式配置：
    @WebServlet(urlPatterns = "/hello02",
        initParams = {
            @WebInitParam(name="uname",value="lina"),
            @WebInitParam(name="count",value="100")
        })
4)XML方式的配置：
    <servlet>
        <servlet-name>Hello02Servlet</servlet-name>
        <servlet-class>servlet.Hello02Servlet</servlet-class>
        <init-param>
            <param-name>uname</param-name>
            <param-value>lina</param-value>
        </init-param>
         <init-param>
            <param-name>count</param-name>
            <param-value>100</param-value>
         </init-param>
     <!--<load-on-startup>1</load-on-startup>-->
        </servlet>
7. ServletContext表示Servlet上下文
    1) 获取上下文对象的4种方式
    2) 通过上下文对象可以获取上下文参数
       context.getInitParameter(key);
