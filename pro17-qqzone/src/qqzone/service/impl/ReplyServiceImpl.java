package qqzone.service.impl;

import qqzone.dao.ReplyDao;
import qqzone.pojo.HostReply;
import qqzone.pojo.Reply;
import qqzone.pojo.Topic;
import qqzone.pojo.UserBasic;
import qqzone.service.HostReplyService;
import qqzone.service.ReplyService;
import qqzone.service.UserBasicService;

import java.util.Date;
import java.util.List;

/**
 * @author fulihua
 * @date 2026/10/6 7:59 PM
 * @description
 */
public class ReplyServiceImpl implements ReplyService {
    private UserBasicService userBasicService;
    private ReplyDao replyDao;
    private HostReplyService hostReplyService;
    @Override
    public List<Reply> getReplyListByTopicId(Integer topicId) {
        List<Reply> replyList = replyDao.getReplyListByTopicId(topicId);
        replyList.forEach(r->{
            //1.给回复加载作者信息，因为详情页面需要显示回复者的头像和昵称
            Integer authorId = r.getAuthor().getId();
            UserBasic author = userBasicService.getUserBasic(authorId);
            r.setAuthor(author);
            //2.给回复加载主人回复信息，因为如果有主人回复，那么需要再当前回复下面展示
            HostReply hostReply = hostReplyService.getHostReplyByReplyId(r.getId());
            r.setHostReply(hostReply);
        });
        return replyList;
    }

    @Override
    public void addReply(Reply reply){
         replyDao.add(reply);
    }

    @Override
    public void delReply(Integer id) {
        //先删除主人回复
        hostReplyService.delHostReplyByReplyId(id);
        //然后再删除回复
        replyDao.delReply(id);
    }

    @Override
    public void delReplyByTopicId(Integer topicId) {
        List<Reply> replyList = replyDao.getReplyListByTopicId(topicId);
        replyList.forEach(r->{
            delReply(r.getId());
        });
    }
}