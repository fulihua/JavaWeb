package activitycommit.test;

import activitycommit.dao.SignUpDao;
import activitycommit.pojo.SignUp;
import myssm.TransManager;

public class TestSignUpDao {
    public static void main(String[] args) {
        // =====新增这一行！手动获取连接，放入ThreadLocal=====
        TransManager.begin();

        SignUpDao signUpDao = new SignUpDao();

        // 测试1：统计活动id=1的报名人数
        //int count = signUpDao.countByActivityId(1);
        //System.out.println("活动1当前报名人数：" + count);

        // 测试2：查询用户1是否报名活动1
        SignUp sign = signUpDao.getByUserAndActivity(1,1);
        System.out.println("SignUp实体：" + sign);
        System.out.println("userId = " + sign.getUserId());
        System.out.println("activityId = " + sign.getActivityId());
        if(sign != null){
            System.out.println("已报名");
        }else{
            System.out.println("未报名");
        }

        // 关闭事务
        TransManager.close();
    }
}
