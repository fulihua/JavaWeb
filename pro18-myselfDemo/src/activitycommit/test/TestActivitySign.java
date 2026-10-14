package activitycommit.test;

import activitycommit.service.ActivitySignService;

/**
 * @author fulihua
 * @date 2026/10/13 4:42 PM
 * @description 报名业务单元测试
 */
public class TestActivitySign {

    public static void main(String[] args) {
        ActivitySignService activitySignService = new ActivitySignService();
        testFullSign(3,2, activitySignService);
    }

    /**
     * 场景1：正常报名，能成功插入报名记录
     * userId=1，activityId=2
     */
    public static void testNormalSign(Integer userId, Integer activityId, ActivitySignService service){
        try{
            System.out.println("=====【测试：正常报名】userId="+userId+" activityId="+activityId);
            service.signUpActivity(userId,activityId);
            System.out.println("✅ 报名成功！");
        }catch (Exception e){
            System.out.println("❌ 报名失败："+e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 场景2：重复报名测试（同一个用户再次报名同一个活动，预期抛出异常）
     */
    public static void testRepeatSign(Integer userId, Integer activityId, ActivitySignService service){
        try{
            System.out.println("=====【测试：重复报名】userId="+userId+" activityId="+activityId);
            service.signUpActivity(userId,activityId);
            System.out.println("✅ 报名成功！");
        }catch (Exception e){
            System.out.println("❌ 预期异常："+e.getMessage());
        }
    }

    /**
     * 场景3：名额已满测试
     * 把活动maxNum设置很小，多执行几次填满名额，再报名就会触发"人数已满"
     */
    public static void testFullSign(Integer userId, Integer activityId, ActivitySignService service){
        try{
            System.out.println("=====【测试：名额已满】userId="+userId+" activityId="+activityId);
            service.signUpActivity(userId,activityId);
            System.out.println("✅ 报名成功！");
        }catch (Exception e){
            System.out.println("❌ 预期异常："+e.getMessage());
        }
    }
}
