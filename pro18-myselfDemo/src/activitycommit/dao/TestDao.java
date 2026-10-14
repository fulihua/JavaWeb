package activitycommit.dao;

import activitycommit.pojo.Activity;
import activitycommit.pojo.User;
import myssm.BaseDao;
import myssm.ConnUtil;
import myssm.TransManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class TestDao extends BaseDao<Activity> {
    public <T> T load(Class<T> clazz, Integer id) {
        Connection conn = TransManager.getConnection();
        System.out.println("conn = " + conn);
        if(conn == null){
            System.out.println("数据库连接获取失败！");
            return null;
        }
        String tableName = "activity";
        String sql = "select * from "+tableName+" where id=?";
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1,id);
            rs = pst.executeQuery();
            if(rs.next()){
                Activity activity = new Activity();
                activity.setId(rs.getInt("id"));
                activity.setTitle(rs.getString("title"));
                activity.setActivityTime(rs.getTimestamp("activity_time"));
                activity.setLocation(rs.getString("location"));
                activity.setDetail(rs.getString("detail"));
                activity.setMaxNum(rs.getInt("max_num"));
                activity.setCreateTime(rs.getTimestamp("create_time"));

                // ============新增外键处理逻辑============
                Integer createUserId = rs.getInt("create_user_id");
                // 判断不是null（数据库字段允许null，rs.getInt如果是null会返回0，所以用wasNull判断）
                if(!rs.wasNull()){
                    UserDao userDao = new UserDao();
                    User createUser = userDao.load(createUserId);
                    activity.setCreateUser(createUser);
                }
                // ========================================
                return (T) activity;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try{
                if(rs != null) rs.close();
                if(pst != null) pst.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

}
