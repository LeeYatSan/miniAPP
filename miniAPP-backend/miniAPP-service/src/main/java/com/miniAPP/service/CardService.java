package com.miniAPP.service;

import com.miniAPP.pojo.FrCard;
import com.miniAPP.pojo.FrLabel;
import com.miniAPP.pojo.FrLabelMap;

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
}
