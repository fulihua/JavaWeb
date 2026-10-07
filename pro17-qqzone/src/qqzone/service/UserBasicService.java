package qqzone.service;

import qqzone.pojo.UserBasic;

import java.util.List;

public interface UserBasicService {
    UserBasic login(String loginId,String pwd);
    //获取userBasic的好友列表
    List<UserBasic> getFriendList(UserBasic userBasic);
    UserBasic getUserBasic(Integer id);
}
