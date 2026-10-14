package activitycommit.service;

import activitycommit.dao.ActivityDao;
import activitycommit.dao.SignUpDao;
import activitycommit.pojo.Activity;
import activitycommit.pojo.SignUp;
import activitycommit.pojo.User;
import myssm.TransManager;

import java.util.List;

/**
 * @author fulihua
 * @date 2026/10/12 9:58 AM
 * @description
 */
public class ActivitySignService {
    private ActivityDao activityDao = new ActivityDao();
    private SignUpDao signUpDao = new SignUpDao();

    public void signUpActivity(Integer userId,Integer activityId){
        try {
            TransManager.begin();

            SignUp existsignup = signUpDao.getByUserAndActivity(userId,activityId);
            if(existsignup!=null){
                throw new RuntimeException("您已经报名过该活动，请勿重复报名！");
            }
            //1.查询活动
            Activity activity = activityDao.selectById(activityId);
            if (activity == null) {
                throw new RuntimeException("活动不存在！");
            }
            //2.统计当前报名人数
            int tempcount = signUpDao.countByActivityId(activityId);
            //3.判断人数是否已满
            if (tempcount >= activity.getMaxNum()) {
                throw new RuntimeException("人数已满！");
            }
            //4.构造报名对象
            SignUp signUp = new SignUp();
            signUp.setUserId(userId);
            signUp.setActivityId(activityId);
            signUp.setUser(new User(userId));
            signUp.setActivity(new Activity(activityId));
            signUp.setSignTime(new java.util.Date());
            //5.插入报名记录
            signUpDao.insertSignUp(signUp);
            TransManager.commit();
        }catch (Exception e){
            TransManager.rollback();
            throw new RuntimeException(e.getMessage());
        }

    }
}