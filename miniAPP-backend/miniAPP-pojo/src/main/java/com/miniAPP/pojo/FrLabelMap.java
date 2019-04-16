package com.miniAPP.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@ApiModel(value = "LabelMap", description = "用户标签映射表")
@Table(name = "fr_label_map")
public class FrLabelMap {
    /**
     * label map ID
     */
    @ApiModelProperty(hidden=true)
    @Id
    @Column(name = "label_map_id")
    private Integer labelMapId;

    /**
     * card ID
     */
    @ApiModelProperty(hidden=true)
    @Column(name = "card_id")
    private String cardId;

    /**
     * label ID
     */
    @ApiModelProperty(hidden=true)
    @Column(name = "label_id")
    private Integer labelId;

    /**
     * 获取label map ID
     *
     * @return label_map_id - label map ID
     */
    public Integer getLabelMapId() {
        return labelMapId;
    }

    /**
     * 设置label map ID
     *
     * @param labelMapId label map ID
     */
    public void setLabelMapId(Integer labelMapId) {
        this.labelMapId = labelMapId;
    }

    /**
     * 获取card ID
     *
     * @return card_id - card ID
     */
    public String getCardId() {
        return cardId;
    }

    /**
     * 设置card ID
     *
     * @param cardId card ID
     */
    public void setCardId(String cardId) {
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