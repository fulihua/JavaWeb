package activitycommit.dao;

import activitycommit.pojo.Activity;
import activitycommit.pojo.SignUp;
import activitycommit.pojo.User;
import myssm.BaseDao;
import myssm.TransManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fulihua
 * @date 2026/10/12 9:43 AM
 * @description
 */
    public class SignUpDao extends BaseDao<SignUp> {

        // 根据活动id，查询该活动所有报名记录
        public List<SignUp> selectByActivityId(Integer activityId) {
            String sql = "select id,user_id,activity_id,sign_time from sign_up where activity_id=?";
            return executeQuery(sql, activityId);
        }

        // 添加报名记录
        public int insertSignUp(SignUp signUp) {
            String sql = "insert into sign_up(user_id,activity_id,sign_time) values (?,?,?)";
            return executeUpdate(sql,
                    signUp.getUser().getId(),
                    signUp.getActivity().getId(),
                    signUp.getSignTime()
            );
        }

        /*
        public List<SignUp> listByActivityId(Integer activityId){
            String sql = "select * from sign_up where activity_id = ?";
            List<SignUp> signUpList = executeQuery(sql,activityId);
            ActivityDao activityDao = new ActivityDao();
            Activity activity = activityDao.loadById(activityId);
            UserDao userDao = new UserDao();
            for(SignUp signUp:signUpList){
                User user = userDao.load(signUp.getUser().getId());
                signUp.setUser(user);
                signUp.setActivity(activity);
            }
            return signUpList;
        }

         */
        public List<SignUp> listByActivityId(Integer activityId){
            List<SignUp> signUpList = new ArrayList<>();
            Connection conn = TransManager.getConnection();
            String sql = "select id,user_id,activity_id,sign_time from sign_up where activity_id = ?";
            PreparedStatement psmt = null;
            ResultSet rs = null;
            try {
                psmt = conn.prepareStatement(sql);
                psmt.setInt(1, activityId);
                rs = psmt.executeQuery();
                while(rs.next()){
                    SignUp signUp = new SignUp();
                    signUp.setId(rs.getInt("id"));

                    // 只把id塞进去
                    User user = new User();
                    user.setId(rs.getInt("user_id"));
                    signUp.setUser(user);

                    Activity activity = new Activity();
                    activity.setId(rs.getInt("activity_id"));
                    signUp.setActivity(activity);

                    signUp.setSignTime(rs.getTimestamp("sign_time"));
                    signUpList.add(signUp);
                }
            }catch (SQLException e){
                e.printStackTrace();
                throw new RuntimeException(e);
            }finally {
                close(rs,psmt,conn);
            }

            // 一次性查完整活动
            ActivityDao activityDao = new ActivityDao();
            Activity activity = activityDao.loadById(activityId);
            UserDao userDao = new UserDao();

            for(SignUp signUp:signUpList){
                User user = userDao.load(signUp.getUser().getId());
                signUp.setUser(user);
                signUp.setActivity(activity);
            }
            return signUpList;
        }
    public SignUp getByUserAndActivity(Integer userId,Integer activityId){
        // 不再需要 as xxx，转换交给工具函数
        String sql = "select * from sign_up where user_id = ? and activity_id = ?";
        return this.load(sql,userId,activityId);
    }

    public int countByActivityId(Integer activityId){
            String sql = "select count(*) from sign_up where activity_id = ?";
            ResultSet rs = null;
            int count = 0;
            try{
                rs =  executeQueryRaw(sql,activityId);
                if(rs.next()){
                    count = rs.getInt(1);
                }
            }catch (SQLException e){
                e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }finally {
                close(rs,psmt,conn);
            }
            return count;
        }


}

