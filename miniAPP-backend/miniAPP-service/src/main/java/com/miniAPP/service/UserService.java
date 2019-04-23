package com.miniAPP.service;

import com.miniAPP.pojo.FrUserLogin;
import com.miniAPP.pojo.VO.UserVO;

public interface UserService {

    /**
     * @Description: 判断用户名是否存在
     * @param openid
     */
     boolean queryOpenidIsExist(String openid);


    /**
     * @Description: 查找用户ID
     * @param openid
     */
    Long queryUserID(String openid);


    /**
     * @Description: 查找用户信息
     * @param userID
     */
    UserVO queryUserInfo(Long userID, String sessionToken);


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
     * @param userLogin
     */
    Long saveUser(FrUserLogin userLogin);


    /**
     * @Description: 用户登录记录
     * @param userID
     */
    void userLoginRec(Long userID);


    /**
     * @Description: 用户注销
     * @param userID
     */
    void userLogout(Long userID);

    /**
     * @Description: 生成用户ID
     */
    Long generateUserID();
}