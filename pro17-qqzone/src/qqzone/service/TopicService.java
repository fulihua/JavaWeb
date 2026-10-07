package qqzone.service;

import qqzone.pojo.Topic;
import qqzone.pojo.UserBasic;

import java.util.List;

public interface TopicService {
    //获取指定用户的日志列表信息
    List<Topic> getTopicList(UserBasic userBasic);
    //根据id查询Topic本身的信息
    Topic getTopic(Integer id);

    Topic getTopicWithReplyList(Integer id);

    void addTopic(Topic topic);

    void delTopic(Integer id);

}
