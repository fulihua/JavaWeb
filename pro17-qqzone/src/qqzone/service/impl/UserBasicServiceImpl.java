package qqzone.service.impl;

import qqzone.pojo.UserBasic;
import qqzone.service.UserBasicService;
import qqzone.dao.UserBasicDao;

import java.util.List;

/**
 * @author fulihua
 * @date 2026/10/5 3:50 PM
 * @description
 */
public class UserBasicServiceImpl implements UserBasicService {
    private UserBasicDao userBasicDao;
    @Override
    public UserBasic login(String loginId, String pwd) {
        return userBasicDao.getUserBasicByLoginIdAndPwd(loginId,pwd);
    }

    @Override
    public UserBasic getUserBasic(Integer id) {
        return userBasicDao.getUserBasic(id);
    }

    @Override
    public List<UserBasic> getFriendList(UserBasic userBasic) {
        return userBasicDao.getUserBasicList(userBasic);


    }
}