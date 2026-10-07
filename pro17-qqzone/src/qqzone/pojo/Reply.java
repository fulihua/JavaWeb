package qqzone.pojo;

import java.util.Date;

/**
 * @author fulihua
 * @date 2026/10/5 2:57 PM
 * @description
 */
public class Reply {
    private Integer id;
    private String content;
    private Date replyDate;
    private UserBasic author;  //M:1
    private Topic topic;    //M:1
    private HostReply hostReply;   //1:1 UK 实际上是1:N

    public Reply() {
    }

    public Reply(Integer id) {
        this.id = id;
    }

    public Reply(String content, Date now, UserBasic userBasic, Topic topic) {
        this.content = content;
        this.replyDate = now;
        this.author = userBasic;
        this.topic = topic;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }

    public UserBasic getAuthor() {
        return author;
    }

    public void setAuthor(UserBasic author) {
        this.author = author;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public HostReply getHostReply() {
        return hostReply;
    }

    public void setHostReply(HostReply hostReply) {
        this.hostReply = hostReply;
    }
}