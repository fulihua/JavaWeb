package activitycommit.controller;

import activitycommit.annotation.Controller;
import activitycommit.dao.UserDao;
import activitycommit.pojo.User;
import activitycommit.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author fulihua
 * @date 2026/10/12 10:41 AM
 * @description
 */
@Controller
    public class UserController {
        private UserService userService = new UserService();

        // 查询单个用户
        public String findUserById(Integer id, HttpSession session, Map<String,Object> resultMap){
            User user = userService.findUserById(id);
            resultMap.put("data",user);
            return "json";
        }

        // 查询全部用户
        public String findAllUser(HttpSession session, Map<String,Object> resultMap){
            List<User> userList = userService.findAllUser();
            resultMap.put("data",userList);
            return "json";
        }

        // 用户注册
        public String registerUser(User user, HttpSession session, Map<String,Object> resultMap){
            try {
                int rows = userService.registerUser(user);
                resultMap.put("code",200);
                resultMap.put("msg", rows>0 ? "注册成功" : "注册失败");
            }catch (Exception e){
                resultMap.put("code",500);
                resultMap.put("msg",e.getMessage());
            }
            return "json";
        }
}