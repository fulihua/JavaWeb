package qqzone.service.impl;

import qqzone.dao.HostReplyDao;
import qqzone.pojo.HostReply;
import qqzone.service.HostReplyService;

/**
 * @author fulihua
 * @date 2026/10/6 9:25 PM
 * @description
 */
public class HostReplyServiceImpl implements HostReplyService {
    private HostReplyDao hostReplyDao;

    @Override
    public HostReply getHostReplyByReplyId(Integer id) {
        return hostReplyDao.getHostReplyByReplyId(id);
    }

    @Override
    public void addHostReply(HostReply hostReply) {
        hostReplyDao.addHostReply(hostReply);
    }

    @Override
    public void delHostReplyByReplyId(Integer replyId) {
        hostReplyDao.delHostReplyByReplyId(replyId);
    }
}