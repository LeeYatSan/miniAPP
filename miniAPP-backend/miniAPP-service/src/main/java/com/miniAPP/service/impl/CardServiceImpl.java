package com.miniAPP.service.impl;

import com.miniAPP.mapper.FrCardMapper;
import com.miniAPP.mapper.FrLabelMapMapper;
import com.miniAPP.mapper.FrLabelMapper;
import com.miniAPP.mapper.FrUserInfoMapper;
import com.miniAPP.pojo.FrCard;
import com.miniAPP.pojo.FrLabel;
import com.miniAPP.pojo.FrLabelMap;
import com.miniAPP.pojo.FrUserInfo;
import com.miniAPP.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Calendar;
import java.util.GregorianCalendar;


@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private FrCardMapper cardMapper;

    @Autowired
    private FrLabelMapper labelMapper;

    @Autowired
    private FrLabelMapMapper labelMapMapper;

    @Autowired
    private FrUserInfoMapper userInfoMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Long saveCard(FrCard c){
        FrCard card=new FrCard();

        Long userID = c.getUserId();
        FrUserInfo userInfo = userInfoMapper.selectByPrimaryKey(userID);
        StringBuilder cardIDstr = new StringBuilder(userID.toString());
        cardIDstr.append(String.format("%05d", userInfo.getTotalCards()));
        Long cardId = Long.parseLong(cardIDstr.toString());

//        card.setCardId(cardId);
//        card.setUserId(c.getUserId());
//        card.setContent(c.getContent());
//        card.setLabelNum(c.getLabelNum());
//        card.setRememberTimes(0);
//        card.setPicUrl(c.getPicUrl());
//        cardMapper.insert(card);

        Calendar cal = new GregorianCalendar();

        c.setCardId(cardId);
        c.setRememberTimes(0);
        c.setCreateTime(cal.getTime());
        c.setLastRememberTime(cal.getTime());
        cal.add(Calendar.DATE, userInfo.getPushFrequency()&0xff);//根据user_info中的pushing_frequency计算下一次推送日期
        c.setNextTime(cal.getTime());

        return card.getCardId();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveLabel(Long userID, Long cardID, String[] labelContents){
        FrLabel label=new FrLabel();
        FrLabelMap labelMap=new FrLabelMap();

        label.setUserId(userID);
        labelMap.setCardId(cardID);

        if(labelContents.length==0){
            label.setLabelContent("Genaral");
            initLabel(label, labelMap);
        }
        else {
            for (String s : labelContents) {
                label.setLabelContent(s);
                initLabel(label, labelMap);
            }
        }
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void initLabel(FrLabel label, FrLabelMap labelMap){

        labelMapper.insert(label);
        label = labelMapper.selectOne(label);
        labelMap.setLabelId(label.getLabelId());
        labelMapMapper.insert(labelMap);
    }

}
