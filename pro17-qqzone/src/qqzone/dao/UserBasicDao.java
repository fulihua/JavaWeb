package qqzone.dao;

import qqzone.pojo.UserBasic;

import java.util.List;

public interface UserBasicDao {
    UserBasic getUserBasicByLoginIdAndPwd(String loginId,String pwd);

    List<UserBasic> getUserBasicList(UserBasic userBasic);

    UserBasic getUserBasic(Integer id);
}

