package book.service;

import book.pojo.User;

/**
 * @author fulihua
 * @date 2026/10/17 8:24 PM
 * @description
 */
public interface UserService {
    User getUserByUnameAndPwd(String uname,String pwd);

    void addUser(User user);
}