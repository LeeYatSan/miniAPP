package com.miniAPP.service;

import com.miniAPP.pojo.User;

public interface UserService {

    /**
     * @Description: 判断用户名是否存在
     */
    boolean queryUsernameIsExist(String username);

    /**
     * @Description: 保存用户信息
     * @param user
     */
    void saveUser(User user);

}
