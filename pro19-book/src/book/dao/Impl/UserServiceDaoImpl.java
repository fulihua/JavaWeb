package book.dao.Impl;

import book.dao.UserServiceDao;
import book.pojo.User;
import myssm.base.BaseDao;

/**
 * @author fulihua
 * @date 2026/10/17 8:28 PM
 * @description
 */
public class UserServiceDaoImpl extends BaseDao<User> implements UserServiceDao {
    @Override
    public User getUserByUnameAndPwd(String uname, String pwd) {
        return load("select * from t_user where uname = ? and pwd = ?",uname,pwd);
    }

    @Override
    public void addUser(User user) {
        executeUpdate("insert into t_user values(0,?,?,?,?)",user.getUname(),user.getPwd(),user.getEmail(),user.getRole());
    }
}