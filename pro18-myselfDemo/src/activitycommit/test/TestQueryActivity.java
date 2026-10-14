package activitycommit.test;

import activitycommit.dao.TestDao;
import myssm.TransManager;
import activitycommit.pojo.Activity;

public class TestQueryActivity {
    public static void main(String[] args) {
        try {
            TransManager.begin();
            TestDao testDao = new TestDao();
            Activity activity = testDao.load(Activity.class, 1);

            if(activity != null){
                System.out.println("✅ 查询成功！");
                System.out.println("活动id：" + activity.getId());
                System.out.println("活动标题：" + activity.getTitle());
                System.out.println("地点：" + activity.getLocation());
                System.out.println("最大报名人数：" + activity.getMaxNum());
                // 打印创建人信息
                if(activity.getCreateUser() != null){
                    System.out.println("活动创建人id：" + activity.getCreateUser().getId());
                    System.out.println("活动创建人姓名：" + activity.getCreateUser().getName());
                    System.out.println("用户创建时间：" + activity.getCreateUser().getCreateTime());
                }
            }else {
                System.out.println("❌ 没有找到这条活动记录");
            }
            TransManager.commit();
        }catch (Exception e){
            TransManager.rollback();
            e.printStackTrace();
        }finally {
            TransManager.close();
        }
    }
}
