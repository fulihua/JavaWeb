package activitycommit.pojo;

import java.util.Date;

/**
 * @author fulihua
 * @date 2026/10/11 7:39 PM
 * @description
 */
public class Activity {
    private String title;
    private Integer id;
    private Date activityTime;
    private String location;
    private String detail;
    private User createUser;
    private Integer maxNum;
    private Date createTime;


    public Activity(String title, Integer id, Date activityTime, String location, String detail, User createUser, Integer maxNum) {
        this.title = title;
        this.id = id;
        this.activityTime = activityTime;
        this.location = location;
        this.detail = detail;
        this.createUser = createUser;
        this.maxNum = maxNum;
    }

    public Activity() {
    }

    public Activity(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(Date activityTime) {
        this.activityTime = activityTime;
    }

    public User getCreateUser() {
        return createUser;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    public Integer getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    @Override
    public String toString() {
        return "Activity{" +
                "title='" + title + '\'' +
                ", id=" + id +
                ", activityTime=" + activityTime +
                ", location='" + location + '\'' +
                ", detail='" + detail + '\'' +
                ", createUser=" + createUser +
                ", maxNum=" + maxNum +
                ", createTime=" + createTime +
                '}';
    }
}