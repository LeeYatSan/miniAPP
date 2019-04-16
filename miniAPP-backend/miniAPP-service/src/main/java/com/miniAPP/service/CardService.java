package com.miniAPP.service;

import com.miniAPP.pojo.FrCard;

public interface CardService {

    /**
     * @Description 保存卡片
     * @param card
     * @return number of saved cards
     */
    public String saveCard(FrCard card);

    /**
     * @Description 保存卡片的标签
     * @param userID, cardID, labelContents
     */
    public void saveLabel(String userID, String cardID, String[] labelContents);
}
