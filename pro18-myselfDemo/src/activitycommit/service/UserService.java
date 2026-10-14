package activitycommit.service;

import activitycommit.dao.UserDao;
import activitycommit.pojo.User;

import java.util.List;

/**
 * @author fulihua
 * @date 2026/10/12 10:13 AM
 * @description
 */
public class UserService {
    private UserDao userDao = new UserDao();

    public List<User> findAllUser(){
        return userDao.selectAll();
    }

    public User findUserById(Integer id){
        return userDao.selectById(id);
    }

    public int registerUser(User user){
        return userDao.insertUser(user);
    }

}