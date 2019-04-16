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

import java.util.Date;
import java.util.List;

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
    public String saveCard(FrCard c){
        FrCard card=new FrCard();
        String cardId = sid.nextShort();
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
    public void saveLabel(String userID, String cardID, String[] labelContents){
        FrLabel label=new FrLabel();
        FrLabelMap labelMap=new FrLabelMap();

        label.setUserId(userID);
        labelMap.setCardId(cardID);

        int labelID;
        if(labelContents.length==0){
            label.setLabelContent("Genaral");
            labelMapper.insert(label);
            label=labelMapper.selectOne(label);
            labelID=label.getLabelId();
            labelMap.setLabelId(labelID);
            labelMapMapper.insert(labelMap);
        }
        else {
            for (String s : labelContents) {
                label.setLabelContent(s);
                labelMapper.insert(label);
                label = labelMapper.selectOne(label);
                labelID = label.getLabelId();

                labelMap.setLabelId(labelID);
                labelMapMapper.insert(labelMap);
            }
        }
    }
}
