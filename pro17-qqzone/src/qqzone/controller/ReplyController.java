package qqzone.controller;

import qqzone.pojo.Reply;
import qqzone.pojo.Topic;
import qqzone.pojo.UserBasic;
import qqzone.service.ReplyService;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author fulihua
 * @date 2026/10/7 4:02 PM
 * @description
 */
public class ReplyController {
    private ReplyService replyService;
    public String addReply(Integer topicId, String content, HttpSession session){
        UserBasic userBasic = (UserBasic) session.getAttribute("userBasic");
        Date now = new Date();
        Reply reply = new Reply(content,now,userBasic,new Topic(topicId));
        replyService.addReply(reply);
        return "redirect:topic.do?oper=detail&id="+topicId;
    }

    //Cannot delete or update a parent row: a foreign key constraint fails
    // (`qqzonedb`.`t_host_reply`, CONSTRAINT `FK_host_reply` FOREIGN KEY (`reply`) REFERENCES `t_reply` (`id`))
    public String delReply(Integer id,HttpSession session){
        replyService.delReply(id);
        Topic topic=(Topic) session.getAttribute("topic");
        return "redirect:topic.do?oper=detail&id="+topic.getId();
        }
    }

