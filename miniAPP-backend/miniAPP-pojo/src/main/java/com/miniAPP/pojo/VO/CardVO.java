package com.miniAPP.pojo.VO;

import com.miniAPP.pojo.FrCard;
import io.swagger.annotations.ApiModel;


@ApiModel(value = "卡片对象", description = "后台返回的卡片信息")
public class CardVO {

    /**
     * 要返回的卡片
     */
    FrCard card;

    /**
     * 卡片所有标签
     */
    Object[] labels;

    public FrCard getCard() {
        return card;
    }

    public void setCard(FrCard card) {
        this.card = card;
    }

    public Object[] getLabels() {
        return labels;
    }

    public void setLabels(Object[] labels) {
        this.labels = labels;
    }
}
