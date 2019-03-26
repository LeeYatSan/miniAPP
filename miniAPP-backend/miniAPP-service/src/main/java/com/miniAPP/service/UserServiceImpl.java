package com.miniAPP.service;

import com.miniAPP.mapper.UserMapper;
import com.miniAPP.pojo.User;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryOpenidIsExist(String openid){
        User user = new User();
        user.setOpenid(openid);
        User result = userMapper.selectOne(user);
        return result == null ? false : true;
    }

//    @Transactional(propagation = Propagation.SUPPORTS)
//    @Override
//    public boolean queryUserInfo(String userID){
//        User user = new User();
//        user.setOpenid(openid);
//        User result = userMapper.selectOne(user);
//        return result == null ? false : true;
//    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveUser(User user){
        String userID = sid.nextShort();
        user.setId(userID);
        userMapper.insert(user);
    }
}
