package activitycommit.test;

import activitycommit.dao.SignUpDao;
import activitycommit.pojo.*;
import activitycommit.pojo.User;
import myssm.TransManager;


import java.util.Date;

/**
 * @author fulihua
 * @date 2026/10/12 2:43 PM
 * @description
 */
public class TestInsertSignUp {
    public static void main(String[] args) {
        try {
            TransManager.begin();
            SignUpDao signUpDao = new SignUpDao();
            SignUp signUp = new SignUp();

            User user = new User();
            user.setId(1);
            Activity activity = new Activity();
            activity.setId(1);

            signUp.setUser(user);
            signUp.setActivity(activity);
            signUp.setSignTime(new Date());

            int rows = signUpDao.insertSignUp(signUp);
            System.out.println(rows > 0 ? "报名成功" : "报名失败");
            TransManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
            TransManager.rollback();
        } finally {
            TransManager.close();
        }
    }
}
