package qqzone.dao.impl;

import myssm.base.BaseDao;
import qqzone.dao.ReplyDao;
import qqzone.pojo.Reply;
import qqzone.service.impl.ReplyServiceImpl;

import java.util.List;

/**
 * @author fulihua
 * @date 2026/10/6 8:12 PM
 * @description
 */
public class ReplyDaoImpl extends BaseDao<Reply> implements ReplyDao {
    @Override
    public List<Reply> getReplyListByTopicId(Integer topicId) {
        return executeQuery("select * from t_reply where topic = ?",topicId);
    }

    @Override
    public void add(Reply reply) {
            System.out.println("=====调试打印=====");
            System.out.println("reply对象：" + reply);
            System.out.println("reply.getAuthor()：" + reply.getAuthor());
            System.out.println("reply.getTopic()：" + reply.getTopic());
            System.out.println("reply.getReplyDate()：" + reply.getReplyDate());

            executeUpdate("insert into t_reply values(0,?,?,?,?)",
                    reply.getContent(),
                    reply.getReplyDate(),
                    reply.getAuthor().getId(),
                    reply.getTopic().getId());
        }

        //executeUpdate("insert into t_reply values(0,?,?,?,?)",reply.getContent(),reply.getReplyDate(),reply.getAuthor().getId(),reply.getTopic().getId());


    @Override
    public void delReply(Integer id) {
        executeUpdate("delete from t_reply where id = ?",id);
    }
}