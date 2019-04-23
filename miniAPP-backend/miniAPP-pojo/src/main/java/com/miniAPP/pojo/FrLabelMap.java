package com.miniAPP.pojo;

import javax.persistence.*;

@Table(name = "fr_label_map")
public class FrLabelMap {
    /**
     * label map ID
     */
    @Id
    @Column(name = "label_map_id")
    private Long labelMapId;

    /**
     * card ID
     */
    @Column(name = "card_id")
    private Long cardId;

    /**
     * label ID
     */
    @Column(name = "label_id")
    private Integer labelId;

    /**
     * 获取label map ID
     *
     * @return label_map_id - label map ID
     */
    public Long getLabelMapId() {
        return labelMapId;
    }

    /**
     * 设置label map ID
     *
     * @param labelMapId label map ID
     */
    public void setLabelMapId(Long labelMapId) {
        this.labelMapId = labelMapId;
    }

    /**
     * 获取card ID
     *
     * @return card_id - card ID
     */
    public Long getCardId() {
        return cardId;
    }

    /**
     * 设置card ID
     *
     * @param cardId card ID
     */
    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    /**
     * 获取label ID
     *
     * @return label_id - label ID
     */
    public Integer getLabelId() {
        return labelId;
    }

    /**
     * 设置label ID
     *
     * @param labelId label ID
     */
    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }
}