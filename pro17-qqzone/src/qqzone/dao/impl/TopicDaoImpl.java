package qqzone.dao.impl;

import myssm.base.BaseDao;
import qqzone.dao.TopicDao;
import qqzone.pojo.Topic;
import qqzone.pojo.UserBasic;

import java.util.List;

/**
 * @author fulihua
 * @date 2026/10/5 8:55 PM
 * @description
 */
public class TopicDaoImpl  extends BaseDao<Topic> implements TopicDao {
    @Override
    public List<Topic> getTopicList(UserBasic userBasic) {
        return executeQuery("SELECT * FROM t_topic WHERE AUTHOR = ?",userBasic.getId());
    }

    @Override
    public Topic getTopic(Integer id) {
        return load("select * from t_topic where id = ?",id);
    }

    @Override
    public void addTopic(Topic topic) {
        executeUpdate("insert into t_topic values(0,?,?,?,?)",topic.getTitle(),topic.getContent(),topic.getTopicDate(),topic.getAuthor().getId());
    }

    @Override
    public void delTopic(Integer id) {
        executeUpdate("delete from t_topic where id =?",id);
    }
}