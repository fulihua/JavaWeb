package qqzone.dao;

import qqzone.pojo.Topic;
import qqzone.pojo.UserBasic;

import java.util.List;

public interface TopicDao {
    List<Topic> getTopicList(UserBasic userBasic);

    Topic getTopic(Integer id);

    void addTopic(Topic topic);

    void delTopic(Integer id);
}
