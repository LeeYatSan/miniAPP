package com.miniAPP.service;
import com.miniAPP.mapper.UserMapper;
import com.miniAPP.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean queryUsernameIsExist(String username){
        User user = new User();
        user.setUsername(username);
        User result = userMapper.selectOne(user);
        return result == null ? false : true;
    }

    @Override
    public void saveUser(User user){

    }
}
