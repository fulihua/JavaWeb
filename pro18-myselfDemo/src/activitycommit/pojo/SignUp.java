package activitycommit.pojo;

import java.util.Date;

/**
 * @author fulihua
 * @date 2026/10/11 7:39 PM
 * @description
 */
public class SignUp {
    private User user ;
    private Integer id;
    private Activity activity;
    private Date signTime;
    private Integer userId;
    private Integer activityId;

    // 补上getter setter
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getActivityId() {
        return activityId;
    }
    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public SignUp(User user, Integer id, Activity activity, Date signTime) {
        this.user = user;
        this.id = id;
        this.activity = activity;
        this.signTime = signTime;
    }

    public SignUp() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    @Override
    public String toString() {
        return "SignUp{" +
                "user=" + user +
                ", id=" + id +
                ", activity=" + activity +
                ", signTime=" + signTime +
                ", userId=" + userId +
                ", activityId=" + activityId +
                '}';
    }
}