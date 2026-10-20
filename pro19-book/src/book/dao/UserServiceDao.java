package book.dao;

import book.pojo.User;

public interface UserServiceDao {
     User getUserByUnameAndPwd(String uname, String pwd);

    void addUser(User user);
}
