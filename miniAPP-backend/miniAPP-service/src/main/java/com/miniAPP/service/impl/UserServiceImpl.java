package com.miniAPP.service.impl;

import com.miniAPP.mapper.FrUserInfoMapper;
import com.miniAPP.mapper.FrUserLoginLogsMapper;
import com.miniAPP.mapper.FrUserLoginMapper;
import com.miniAPP.mapper.FrUserRegisterInfoMapper;
import com.miniAPP.pojo.FrUserInfo;
import com.miniAPP.pojo.FrUserLogin;
import com.miniAPP.pojo.FrUserLoginLogs;
import com.miniAPP.pojo.FrUserRegisterInfo;
import com.miniAPP.pojo.VO.UserVO;
import com.miniAPP.service.UserService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Calendar;
import java.util.Date;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private FrUserLoginMapper userLoginMapper;

    @Autowired
    private FrUserLoginLogsMapper userLoginLogsMapper;

    @Autowired
    private FrUserInfoMapper userInfoMapper;

    @Autowired
    private FrUserRegisterInfoMapper userRegisterInfoMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryOpenidIsExist(String openid){
        FrUserLogin user = new FrUserLogin();
        user.setUserOpenid(openid);
        FrUserLogin userLogin = userLoginMapper.selectOne(user);
        return userLogin == null ? false : true;
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public String queryUserID(String openid){
        FrUserLogin user = new FrUserLogin();
        user.setUserOpenid(openid);
        return userLoginMapper.selectOne(user).getUserId();
    }



    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public UserVO queryUserInfo(String userID){
        FrUserLogin userLogin = new FrUserLogin();
        FrUserInfo userInfo = new FrUserInfo();
        UserVO userVO = new UserVO();
        userVO.setID(userID);
        userLogin.setUserId(userID);
        userInfo.setUserId(userID);
        userLogin = userLoginMapper.selectOne(userLogin);
        userInfo = userInfoMapper.selectOne(userInfo);
        userVO.setState(userLogin.getUserState());
        userVO.setLoginDays(userInfo.getLoginDays());
        userVO.setTotalCards(userInfo.getTotalCards());
        userVO.setForgetCards(userInfo.getForgetCards());
        return userVO;
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
    public String saveUser(FrUserLogin userLogin){
        String userID = sid.nextShort();
        FrUserRegisterInfo userRegisterInfo = new FrUserRegisterInfo();
        FrUserInfo userInfo = new FrUserInfo();

        userLogin.setUserId(userID);
        userLogin.setLastLoginTime(new Date());
        userRegisterInfo.setUserId(userID);
        userRegisterInfo.setRegisterTime(new Date());
        userInfo.setUserId(userID);
        userInfo.setLoginDays(0);
        userInfo.setTotalCards(0);
        userInfo.setForgetCards(0);

        userLoginMapper.insert(userLogin);
        userRegisterInfoMapper.insert(userRegisterInfo);
        userInfoMapper.insert(userInfo);

        return userID;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void userLoginRec(String userID){
        FrUserLoginLogs userLoginLogs = new FrUserLoginLogs();
        FrUserInfo userInfo = new FrUserInfo();
        FrUserLogin userLogin = new FrUserLogin();

        userLogin.setUserId(userID);
        userLogin = userLoginMapper.selectOne(userLogin);
        userInfo.setUserId(userID);
        userInfo = userInfoMapper.selectOne(userInfo);

        Calendar lastLoginDate = Calendar.getInstance();
        Calendar currentDate = Calendar.getInstance();
        lastLoginDate.setTime(userLogin.getLastLoginTime());

        //如果不是同一天登录，登录天数加一
        if(currentDate.after(lastLoginDate)){
            userInfo.setLoginDays(userInfo.getLoginDays()+1);
            userInfoMapper.updateByPrimaryKeySelective(userInfo);
        }

        userLoginLogs.setUserId(userID);
        userLoginLogs.setLoginTime(new Date());
        userLoginLogsMapper.insert(userLoginLogs);

    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void userLogout(String userID){

        FrUserLogin userLogin = new FrUserLogin();

        userLogin.setUserId(userID);
        userLogin = userLoginMapper.selectOne(userLogin);
        userLogin.setUserState(0);

        userLoginMapper.updateByPrimaryKeySelective(userLogin);
    }
}