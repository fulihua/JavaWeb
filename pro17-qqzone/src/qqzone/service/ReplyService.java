package qqzone.service;

import qqzone.pojo.Reply;
import qqzone.pojo.Topic;
import qqzone.pojo.UserBasic;

import java.util.Date;
import java.util.List;

/**
 * @author fulihua
 * @date 2026/10/6 7:57 PM
 * @description
 */
public interface ReplyService {
    //查询topicId关联的回复列表
    List<Reply> getReplyListByTopicId(Integer id);

    void addReply(Reply reply);

    void delReply(Integer id);

    void delReplyByTopicId(Integer topicId);
}