package com.miniAPP.service.impl;

import com.miniAPP.mapper.FrCardMapper;
import com.miniAPP.mapper.FrLabelMapMapper;
import com.miniAPP.mapper.FrLabelMapper;
import com.miniAPP.pojo.FrCard;
import com.miniAPP.pojo.FrLabel;
import com.miniAPP.pojo.FrLabelMap;
import com.miniAPP.service.CardService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private FrCardMapper cardMapper;

    @Autowired
    private FrLabelMapper labelMapper;

    @Autowired
    private FrLabelMapMapper labelMapMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Long saveCard(FrCard c){
        FrCard card=new FrCard();

        //[更改]cardID = userID + （5位total_cards+1）     cardID type: Long
        // e.g.: cardID: 0000032401300001 = userID:00000324013 + 5位total_cards+1 : 0+1 -> 00001
        Long cardId = sid.nextShort();


        card.setCardId(cardId);


        card.setUserId(c.getUserId());
        card.setContent(c.getContent());
        card.setLabelNum(c.getLabelNum());
        card.setRememberTimes(0);
        card.setPicUrl(c.getPicUrl());
        cardMapper.insert(card);
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
