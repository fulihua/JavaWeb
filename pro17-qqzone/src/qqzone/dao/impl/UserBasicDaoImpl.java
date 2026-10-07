package qqzone.dao.impl;

import myssm.base.BaseDao;
import qqzone.pojo.UserBasic;
import qqzone.dao.UserBasicDao;

import java.util.List;

/**
 * @author fulihua
 * @date 2026/10/5 3:55 PM
 * @description
 */
public class UserBasicDaoImpl extends BaseDao<UserBasic> implements UserBasicDao {
    @Override
    public List<UserBasic> getUserBasicList(UserBasic userBasic) {
        String sql = "select t3.* from t_user_basic t1 left join t_friend t2 on t1.id = t2.uid\n"+
                "inner join t_user_basic t3 on t2.fid = t3.id\n"+
                "where t1.id = ?";
        return executeQuery(sql,userBasic.getId());
    }

    @Override
    public UserBasic getUserBasicByLoginIdAndPwd(String loginId, String pwd) {
        return load("select * from t_user_basic where loginId = ? and pwd = ?",loginId,pwd);

    }

    @Override
    public UserBasic getUserBasic(Integer id) {
        return load("select * from t_user_basic where id = ?",id);
    }
}