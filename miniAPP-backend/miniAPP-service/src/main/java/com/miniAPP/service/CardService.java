package com.miniAPP.service;

import com.miniAPP.pojo.FrCard;
import com.miniAPP.pojo.FrLabel;
import com.miniAPP.pojo.FrLabelMap;
import com.miniAPP.pojo.VO.CardNumDetailVO;
import com.miniAPP.pojo.VO.CardVO;
import com.miniAPP.utils.JSONResult;

import java.util.List;

public interface CardService {

    /**
     * @Description 保存卡片
     * @param card
     * @return cardID
     */
    public Long saveCard(FrCard card);

    /**
     * @Description 修改卡片
     * @param card
     */
    public void editCard(FrCard card);

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

    /**
     * @Description 获取待推送的用户列表
     */
    public List<Long> queryUserNeededToBeNoticed();

    /**
     * @Description 获取用户数量信息
     */
    public CardNumDetailVO queryCardNumDetail(Long userID);

    /**
     * @Description 获取未记住的卡片的数量
     */
    public int queryUnfamiliarCardNum(Long userID);

    /**
     * @Description 获取未记住的卡片
     */
    public List<FrCard> getUnFamiliarCard(Long userID);


    /**
     * @Description 获取卡片信息（FrCard+Frlabel）
     * @param card
     * @return
     */
    public CardVO getCardLabels(FrCard card);


    /**
     * @Description 获取一组卡片信息（FrCard+Frlabel）
     * @param cards
     * @return
     */
    public Object[] getEachCardLabels(List<FrCard> cards);

    /**
     * @Description 图片的OCR功能
     */
    public JSONResult Ocr(String picUrl);

}
