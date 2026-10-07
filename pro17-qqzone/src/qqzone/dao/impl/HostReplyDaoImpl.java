package qqzone.dao.impl;

import myssm.base.BaseDao;
import qqzone.dao.HostReplyDao;
import qqzone.pojo.HostReply;

/**
 * @author fulihua
 * @date 2026/10/6 9:27 PM
 * @description
 */
public class HostReplyDaoImpl extends BaseDao<HostReply> implements HostReplyDao {
    @Override
    public HostReply getHostReplyByReplyId(Integer replyId) {
        return (HostReply)load("select * from t_host_reply where reply = ?",replyId);
    }

    @Override
    public void addHostReply(HostReply hostReply) {
        executeQuery("insert into t_host_reply values(0,?,?,?,?)",hostReply.getContent(),hostReply.getHostReplyDate(),hostReply.getAuthor().getId(),hostReply.getReply().getId());
    }

    @Override
    public void delHostReplyByReplyId(Integer replyId) {
        executeUpdate("delete from t_host_reply where reply=?",replyId);
    }
}