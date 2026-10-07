package qqzone.service;

import qqzone.pojo.HostReply;

public interface HostReplyService {

    //根据回复的id获取对应的主人回复
    HostReply getHostReplyByReplyId(Integer id);

    void addHostReply(HostReply hostReply);
    //根据replyId删除指定的HostReply
    void delHostReplyByReplyId(Integer replyId);
}
