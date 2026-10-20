package book.service.Impl;

import book.dao.UserServiceDao;
import book.pojo.User;
import book.service.UserService;

/**
 * @author fulihua
 * @date 2026/10/17 8:26 PM
 * @description
 */
public class UserServiceImpl implements UserService {
    private UserServiceDao userServiceDao;
    @Override
    public User getUserByUnameAndPwd(String uname, String pwd) {
        return userServiceDao.getUserByUnameAndPwd(uname,pwd);
    }

    @Override
    public void addUser(User user) {
        userServiceDao.addUser(user);
    }
}