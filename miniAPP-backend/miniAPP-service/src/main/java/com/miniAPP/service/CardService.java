package com.miniAPP.service;

import com.miniAPP.pojo.FrCard;
import com.miniAPP.pojo.FrLabel;
import com.miniAPP.pojo.FrLabelMap;

import java.util.Date;

public interface CardService {

    /**
     * @Description 保存卡片
     * @param card
     * @return number of saved cards
     */
    public Long saveCard(FrCard card);

    /**
     * @Description 保存卡片的标签
     * @param userID, cardID, labelContents
     */
    public void saveLabel(Long userID, Long cardID, String[] labelContents);

    /**
     * @Description 初始化标签
     * @param label, labelMap
     */
    public void initLabel(FrLabel label, FrLabelMap labelMap);

    /**
     * @Description 通过cardID查找卡片
     * @parm cardID
     */
    public FrCard queryCardByCardID(Long cardID);

    /**
     * @Description 设置下一次记忆时间
     * @parm userID, cardID
     */
    public FrCard nextTime(FrCard frCard, boolean forget);
}
