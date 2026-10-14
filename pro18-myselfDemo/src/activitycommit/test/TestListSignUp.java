package activitycommit.test;

import activitycommit.dao.SignUpDao;
import activitycommit.pojo.SignUp;
import myssm.TransManager;

import java.util.List;

public class TestListSignUp {
    public static void main(String[] args) {
        try {
            TransManager.begin();
            SignUpDao signUpDao = new SignUpDao();
            List<SignUp> list = signUpDao.listByActivityId(1);

            for (SignUp signUp : list) {
                System.out.println("报名id:"+signUp.getId());
                System.out.println("报名用户姓名:"+signUp.getUser().getName());
                System.out.println("活动标题:"+signUp.getActivity().getTitle());
                System.out.println("活动地点:"+signUp.getActivity().getLocation());
                System.out.println("报名时间:"+signUp.getSignTime());
                System.out.println("========");
            }
            TransManager.commit();
        }catch (Exception e){
            e.printStackTrace();
            TransManager.rollback();
        }finally {
            TransManager.close();
        }
    }
}
