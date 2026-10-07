package qqzone.controller;

import jdk.jshell.spi.ExecutionControl;
import qqzone.pojo.Topic;
import qqzone.pojo.UserBasic;
import qqzone.service.TopicService;
import qqzone.service.UserBasicService;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @author fulihua
 * @date 2026/10/6 7:01 PM
 * @description
 */
public class TopicController {
    private TopicService topicService;
    private UserBasicService userBasicService;

    public String getTopicList(Integer id, HttpSession session){
        //根据id查询对应的UserBasic
        UserBasic userBasic = userBasicService.getUserBasic(id);
        List<Topic> topicList = topicService.getTopicList(userBasic);
        userBasic.setTopicList(topicList);
        session.setAttribute("friend",userBasic);

        return "index";
    }

    public String detail(Integer id,HttpSession session){
        Topic topic = topicService.getTopicWithReplyList(id);
        session.setAttribute("topic",topic);
        return "frames/detail";
    }

    public String getTopicList2(Integer id, HttpSession session){
        //根据id查询对应的UserBasic
        UserBasic userBasic = userBasicService.getUserBasic(id);
        List<Topic> topicList = topicService.getTopicList(userBasic);
        userBasic.setTopicList(topicList);
        session.setAttribute("friend",userBasic);

        return "frames/main";
    }

    public String addTopic(String title,String content,HttpSession session){
        UserBasic author = (UserBasic) session.getAttribute("userBasic");
        Topic topic = new Topic(title,content,new Date(),author);
        topicService.addTopic(topic);
        return "redirect:topic.do?oper=getTopicList2&id="+author.getId();
    }

    public String delTopic(Integer id,HttpSession session){
        topicService.delTopic(id);
        UserBasic userBasic = (UserBasic)session.getAttribute("userBasic");
        return "redirect:topic.do?oper=getTopicList2&id="+userBasic.getId();
    }
}