package qqzone.controller;

import qqzone.pojo.Topic;
import qqzone.pojo.UserBasic;
import qqzone.service.TopicService;
import qqzone.service.UserBasicService;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author fulihua
 * @date 2026/10/5 3:38 PM
 * @description
 */
public class UserController {
    private static final String PAGE_INDEX = "index";
    private static final String LOGIN_SUCC = "redirect:user.do";
    private static final String LOGIN_FAIL= "redirect:page?page=login";
    private UserBasicService userBasicService;
    private TopicService topicService;


    public String login(String loginId, String pwd, HttpSession session){
        System.out.println("【Controller收到】loginId=" + loginId + " | pwd=" + pwd);
        UserBasic userBasic = userBasicService.login(loginId,pwd);
        System.out.println("【Service返回】userBasic = " + userBasic);
        if(userBasic!=null){
            session.setAttribute("userBasic",userBasic);//userBasic这个key用来代表登陆者
            session.setAttribute("friend",userBasic);//friend这个key用来代表当前空间是谁的
            return LOGIN_SUCC;
        }else {
            return LOGIN_FAIL;
        }

    }
    public String index(HttpSession session){
        //从session中取出当前登录者的信息
        UserBasic userBasic = (UserBasic)session.getAttribute("userBasic");

        //1.加载好友列表
        List<UserBasic> friendList = userBasicService.getFriendList(userBasic);
        //2.加载日志列表
        //java.lang.IllegalArgumentException:
        // Can not set qqzone.pojo.UserBasic field com.atguigu.qqzone.pojo.Topic.author
        // to java.lang.Integer
        //原因：查询t_topic表，得到的author这一列的值是int类型。而pojo中author这个属性却是UserBasic类型
        //因此不能把Integer赋值给UserBasic
        //那么此时获取到的数字和UserBasic有没有关系呢？
        //肯定是有关系的。当前获取到的Integer的数字是UserBasic的id值
        //假设当前获取到的数字是1，也就意味着当前的作者的id是1
        //那么我们可以通过UserBasic author = new UserBasic(1)创建出一个UserBasic实例，然后再赋值
        List<Topic> topicList = topicService.getTopicList(userBasic);

        userBasic.setTopicList(topicList);
        userBasic.setFriendList(friendList);

        return PAGE_INDEX;

    }

}