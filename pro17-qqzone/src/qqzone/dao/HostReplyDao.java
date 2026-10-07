package qqzone.dao;

import qqzone.pojo.HostReply;

public interface HostReplyDao {

    HostReply getHostReplyByReplyId(Integer replyId);

    void addHostReply(HostReply hostReply);

    void delHostReplyByReplyId(Integer replyId);
}
