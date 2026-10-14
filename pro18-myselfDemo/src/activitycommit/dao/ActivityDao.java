package activitycommit.dao;

import activitycommit.pojo.Activity;
import activitycommit.pojo.User;
import myssm.BaseDao;
import myssm.TransManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 * @author fulihua
 * @date 2026/10/12 9:40 AM
 * @description
 */
    public class ActivityDao extends BaseDao<Activity> {
        // 查询所有活动
        public List<Activity> selectAll() {
            String sql = "select id,title,activity_time,location,detail,create_user_id,max_num,create_time from activity";
            return executeQuery(sql);
        }

        // 根据id查询活动
        public Activity selectById(Integer id) {
            String sql = "select id,title,activity_time,location,detail,create_user_id,max_num,create_time from activity where id=?";
            return load(sql, id);
        }

        // 新增活动
        public int insertActivity(Activity activity) {
            String sql = "insert into activity(title,activity_time,location,detail,create_user_id,max_num,create_time) values (?,?,?,?,?,?,?)";
            return executeUpdate(sql,
                    activity.getTitle(),
                    activity.getActivityTime(),
                    activity.getLocation(),
                    activity.getDetail(),
                    activity.getCreateUser().getId(),
                    activity.getMaxNum(),
                    new java.util.Date()
            );
        }

    public Activity loadById(Integer id){
        String sql = "select * from activity where id = ?";
        return load(sql,id);
    }

 /*
public Activity loadById(Integer id) {
    Connection conn = TransManager.getConnection();
    String sql = "select * from activity where id = ?";
    PreparedStatement psmt = null;
    ResultSet rs = null;
    Activity activity = null;
    try {
        psmt = conn.prepareStatement(sql);
        psmt.setInt(1, id);
        rs = psmt.executeQuery();
        if (rs.next()) {
            activity = new Activity();
            activity.setId(rs.getInt("id"));
            activity.setTitle(rs.getString("title"));
            activity.setLocation(rs.getString("location"));
            activity.setDetail(rs.getString("detail"));
            activity.setActivityTime(rs.getTimestamp("activity_time"));
            activity.setMaxNum(rs.getInt("max_num"));

            User createUser = new User();
            createUser.setId(rs.getInt("create_user_id"));
            activity.setCreateUser(createUser);

            activity.setCreateTime(rs.getTimestamp("create_time"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        close(rs, psmt, conn);
    }
    return activity;
}

 */


}

