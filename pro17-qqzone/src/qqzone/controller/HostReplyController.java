package qqzone.controller;

import qqzone.pojo.HostReply;
import qqzone.pojo.Reply;
import qqzone.pojo.Topic;
import qqzone.pojo.UserBasic;
import qqzone.service.HostReplyService;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author fulihua
 * @date 2026/10/7 4:42 PM
 * @description
 */
public class HostReplyController {
    private HostReplyService hostReplyService;
    public String addHostReply(String content, Integer replyId, HttpSession session){
        UserBasic author = (UserBasic) session.getAttribute("userBasic");
        Topic topic=(Topic)session.getAttribute("topic") ;
        HostReply hostReply = new HostReply(content,new Date(),author,new Reply(replyId));
        hostReplyService.addHostReply(hostReply);
        return "redirct:topic.do?oper=detail&id="+topic.getId();
    }
}