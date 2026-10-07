package qqzone.service.impl;

import qqzone.dao.TopicDao;
import qqzone.pojo.Reply;
import qqzone.pojo.Topic;
import qqzone.pojo.UserBasic;
import qqzone.service.ReplyService;
import qqzone.service.TopicService;
import qqzone.service.UserBasicService;

import java.util.List;

/**
 * @author fulihua
 * @date 2026/10/5 8:49 PM
 * @description
 */
public class TopicServiceImpl implements TopicService {
    private TopicDao topicDao;
    private UserBasicService userBasicService;
    private ReplyService replyService;
    @Override
    public List<Topic> getTopicList(UserBasic userBasic) {
        return topicDao.getTopicList(userBasic);
    }

    @Override
    public Topic getTopic(Integer id) {
        return topicDao.getTopic(id);
    }

    @Override
    public Topic getTopicWithReplyList(Integer id) {
        //查询topic信息，但是这个topic里面的作者只有id值，没有头像和昵称，而我们页面上需要展示作者的头像和昵称
        Topic topic = getTopic(id);
        //查询这篇日志的作者信息（主要是头像和昵称）
        UserBasic author = userBasicService.getUserBasic(topic.getAuthor().getId());
        topic.setAuthor(author);

        List<Reply> replyList = replyService.getReplyListByTopicId(id);
        topic.setReplyList(replyList);
        return topic;
    }

    @Override
    public void addTopic(Topic topic) {
        topicDao.addTopic(topic);
    }

    @Override
    public void delTopic(Integer id) {
        //1.删除关联的回复信息
        replyService.delReplyByTopicId(id);
        //2.删除日志信息
        topicDao.delTopic(id);
    }

}