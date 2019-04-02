package com.miniAPP.service;

import com.miniAPP.pojo.User;

public interface UserService {

    /**
     * @Description: 判断用户名是否存在
     */
    boolean queryOpenidIsExist(String openid);

//    /**
//     * @Description: 查找用户信息
//     */
//    boolean queryUserInfo(String userID);
//
//    /**
//     * @Description: 更新用户信息
//     */
//    boolean updateUserInfo(String userID);

    /**
     * @Description: 保存用户信息
     * @param user
     */
    void saveUser(User user);

}