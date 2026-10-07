package qqzone.dao;

import qqzone.pojo.Reply;

import java.util.List;

public interface ReplyDao {
    List<Reply> getReplyListByTopicId(Integer topicId);

    void add(Reply reply);

    void delReply(Integer id);
}
