package com.miniAPP.service.impl;

import com.miniAPP.mapper.FrCardMapper;
import com.miniAPP.mapper.FrUserFormidMapper;
import com.miniAPP.mapper.FrUserLoginMapper;
import com.miniAPP.pojo.FrCard;
import com.miniAPP.pojo.FrUserFormid;
import com.miniAPP.service.FormIDService;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.BlockingQueue;

@Service
public class FormIDServiceImpl implements FormIDService {

    @Autowired
    FrUserFormidMapper frUserFormidMapper;

    @Autowired
    FrUserLoginMapper frUserLoginMapper;

    @Autowired
    FrCardMapper frCardMapper;


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addFormID(Long userID, String formID){

        ArrayList<FrUserFormid> formidlist = (ArrayList<FrUserFormid>) frUserFormidMapper.getFormIDListByUserID(userID);
        //如果不存在formid队列，创建一个
        if(formidlist == null) {
            formidlist = new ArrayList<>(50);
        }
        //如果队列满了，就把对头的一个元素去掉
        if(formidlist.size() == 50) {
            formidlist.remove(0);
        }
        //添加formid
        FrUserFormid newFrUserFormid = new FrUserFormid();
        newFrUserFormid.setFormId(formID);
        newFrUserFormid.setUserId(userID);
        newFrUserFormid.setUserOpenid(frUserLoginMapper.selectByPrimaryKey(userID).getUserOpenid());
        newFrUserFormid.setGetTime(Calendar.getInstance().getTime());
        frUserFormidMapper.insert(newFrUserFormid);
        formidlist.add(newFrUserFormid);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public String getFormid(Long userID){

        if (userID == null) {
            return null;
        }
        List<FrUserFormid> formidlist = frUserFormidMapper.getFormIDListByUserID(userID);
        if(formidlist == null) {
            return null;
        }
        long nowtime = System.currentTimeMillis();

        FrCard frCard = frCardMapper.queryUnFamiliarCard(userID).get(0);

        FrUserFormid formid;
        //如果超过7天就重新获取一个
        do {
            //如果队列空了就返回null
            if(formidlist.isEmpty()) {
                return null;
            }
            formid = formidlist.get(0);
            formidlist.remove(0);
            frUserFormidMapper.deleteFormid(formid.getFormId());
        }while(nowtime - formid.getGetTime().getTime() > 604800000);
        return formid.getFormId();
    }
}
