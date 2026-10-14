package activitycommit.service;

import activitycommit.dao.ActivityDao;
import activitycommit.pojo.Activity;

import java.util.List;

/**
 * @author fulihua
 * @date 2026/10/12 10:17 AM
 * @description
 */
public class ActivityService {
    private ActivityDao activityDao = new ActivityDao();

    public List<Activity> findAllActivity(){
        return activityDao.selectAll();
    }

    public Activity findActivityById(Integer id){
        return activityDao.selectById(id);
    }

    public int publishActivity(Activity activity){
        return activityDao.insertActivity(activity);
    }

}