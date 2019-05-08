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
import java.util.Date;
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

    private static final long forgettingCurve[] = {
            0,                  // 0->创建日
            1*24*60*60*1000L,	// 1->1天
            2*24*60*60*1000L,	// 2->2天
            4*24*60*60*1000L,	// 3->4天
            7*24*60*60*1000L,	// 4->7天
            15*24*60*60*1000L,	// 5->15天
            31*24*60*60*1000L	// 6->31天
                                // 7->熟记
    };

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Long saveCard(FrCard c){

        Long userID = c.getUserId();
        FrUserInfo userInfo = userInfoMapper.selectByPrimaryKey(userID);
        StringBuilder cardIDstr = new StringBuilder(userID.toString());
        cardIDstr.append(String.format("%05d", userInfo.getTotalCards()));
        Long cardId = Long.parseLong(cardIDstr.toString());

        Calendar cal = new GregorianCalendar();

        c.setCardId(cardId);
        c.setRememberTimes(0);
        c.setMemoLevel((byte)0);
        c.setCreateTime(cal.getTime());
        c.setLastRememberTime(cal.getTime());
        cal.add(Calendar.DATE, userInfo.getPushFrequency()&0xff);//根据user_info中的pushing_frequency计算下一次推送日期
        c.setNextTime(cal.getTime());

        userInfo.setTotalCards(userInfo.getTotalCards()+1);

        cardMapper.insert(c);
        userInfoMapper.updateByPrimaryKeySelective(userInfo);

        return c.getCardId();
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

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public FrCard queryCardByCardID(Long cardID){

        FrCard card = new FrCard();
        card.setCardId(cardID);
        return cardMapper.selectOne(card);
    }

    /**
     *
     * @param frCard 当前卡片
     * @param forget 是否忘记：false：没忘记 true：忘记
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public FrCard nextTime(FrCard frCard, boolean forget){

        byte currMemoLevel = frCard.getMemoLevel();
        if(!forget){
            if(currMemoLevel < 6){
                currMemoLevel = (byte)(currMemoLevel+1);
                frCard.setMemoLevel(currMemoLevel);
                frCard.setNextTime(new Date((new Date()).getTime() + forgettingCurve[currMemoLevel]));
                cardMapper.updateByPrimaryKeySelective(frCard);
            }
            else if(currMemoLevel == 6){
                frCard.setMemoLevel((byte)(currMemoLevel+1));
                cardMapper.updateByPrimaryKeySelective(frCard);
            }
        }
        else {
            currMemoLevel = (byte)1;
            frCard.setMemoLevel(currMemoLevel);
            frCard.setNextTime(new Date((new Date()).getTime() + forgettingCurve[currMemoLevel]));
            cardMapper.updateByPrimaryKeySelective(frCard);
        }
        frCard.setRememberTimes(frCard.getRememberTimes()+1);
        frCard.setLastRememberTime(Calendar.getInstance().getTime());
        return frCard;
    }
}
