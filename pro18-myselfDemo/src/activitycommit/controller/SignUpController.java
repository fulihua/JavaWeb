package activitycommit.controller;

import activitycommit.annotation.Controller;
import activitycommit.service.ActivitySignService;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author fulihua
 * @date 2026/10/12 10:34 AM
 * @description
 */
@Controller
public class SignUpController {
    private ActivitySignService activitySignService = new ActivitySignService();

    public String signUpActivity(Integer userId, Integer activityId, HttpSession session, Map<String, Object> resultMap) {
        try {
            // 简单参数校验
            if(userId == null || activityId == null){
                resultMap.put("code",400);
                resultMap.put("msg","参数不能为空");
                return "json";
            }
            activitySignService.signUpActivity(userId, activityId);
            resultMap.put("code", 200);
            resultMap.put("msg", "报名成功");
    }catch(Exception e)
    {
        resultMap.put("code", 500);
        resultMap.put("msg", e.getMessage());
    }
        return"json";
}

}