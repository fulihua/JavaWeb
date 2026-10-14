package activitycommit.controller;

import activitycommit.annotation.Controller;
import activitycommit.dao.ActivityDao;
import activitycommit.pojo.Activity;
import activitycommit.pojo.User;
import activitycommit.service.ActivityService;
import myssm.TransManager;
import activitycommit.annotation.RequestMapping;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;



/**
 * @author fulihua
 * @date 2026/10/12 10:44 AM
 * @description
 */
@Controller
@RequestMapping("/activity")
public class ActivityController {
    private ActivityService activityService = new ActivityService();
    private ActivityDao activityDao = new ActivityDao();

    // 查询单个活动详情
    @RequestMapping("/findById")
    public String findActivityById(Integer id, HttpSession session, Map<String,Object> resultMap){
        Activity activity = activityService.findActivityById(id);
        resultMap.put("data",activity);
        return "json";
    }

    // 查询全部活动列表
    @RequestMapping("/list")
    public String findAllActivity(HttpSession session, Map<String,Object> resultMap){
        List<Activity> activityList = activityService.findAllActivity();
        resultMap.put("data",activityList);
        return "json";
    }
    @RequestMapping("/publish")
    // 发布活动
    public String publishActivity(Activity activity, HttpSession session, Map<String,Object> resultMap){
        try {
            int rows = activityService.publishActivity(activity);
            resultMap.put("code",200);
            resultMap.put("msg", rows>0 ? "活动发布成功" : "活动发布失败");
        }catch (Exception e){
            resultMap.put("code",500);
            resultMap.put("msg",e.getMessage());
        }
        return "json";
    }
    @RequestMapping("/list")
    public String list(Integer userId, Integer activityId, HttpSession session,HttpServletRequest req, Map<String,Object> resultMap){
        List<Activity> activityList = activityDao.selectAll();
        resultMap.put("data",activityList);
        resultMap.put("total",activityList.size());
        return "json";
    }
@RequestMapping("/detail")
    public String detail(Integer userId,Integer activityId,HttpSession session,HttpServletRequest req,Map<String ,Object> resultMap){
        if(activityId==null){
            resultMap.put("code",400);
            resultMap.put("msg","活动id不能为空");
            return "json";
        }
         Activity activity = activityDao.selectById(activityId);
        if(activity==null){
            resultMap.put("code",400);
            resultMap.put("msg","找不到该活动");
            return "json";
        }
        resultMap.put("data",activity);
        resultMap.put("code",200);
        return "json";
    }
    @RequestMapping("/add")
    public String add(Integer userId,Integer activityId,HttpSession session,HttpServletRequest req,Map<String ,Object> resultMap){
        String title = req.getParameter("title");
        String location = req.getParameter("location");
        String detail = req.getParameter("detail");
        Integer maxNum = null;
        if(req.getParameter("maxNum")!=null) {
            maxNum = Integer.parseInt(req.getParameter("maxNum"));
        }
       String createTimeStr = req.getParameter("activityTime");
        if(title==null||title.trim().isEmpty()){
            resultMap.put("code",400);
            resultMap.put("msg","标题不能为空");
            return "json";
        }
        Activity activity = new Activity();
        activity.setTitle(title);
        activity.setLocation(location);
        activity.setDetail(detail);
        activity.setMaxNum(maxNum);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try{
            Date acivityTime = sdf.parse(createTimeStr);
            activity.setActivityTime(acivityTime);
        }catch (ParseException e){
            resultMap.put("code",400);
            resultMap.put("msg", "活动时间格式错误,要求 yyyy-MM-dd HH:mm");
            return "json";
        }
        User createUser = new User();
        createUser.setId(userId);
        activity.setCreateUser(createUser);
        TransManager.begin();
        try{
            int rows = activityDao.insertActivity(activity);
            if(rows>0){
                TransManager.commit();
                resultMap.put("code",200);
                resultMap.put("msg","新增活动成功！");
            }else{
                TransManager.rollback();
                resultMap.put("code",500);
                resultMap.put("msg","新增失败！");
            }
        }catch (Exception e){
            TransManager.rollback();
            e.printStackTrace();
            resultMap.put("code",500);
            resultMap.put("msg","服务器异常，新增服务出错！");
        }
        return "json";
    }


}