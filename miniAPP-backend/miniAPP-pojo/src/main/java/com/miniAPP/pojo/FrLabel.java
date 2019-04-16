package com.miniAPP.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@ApiModel(value = "Label", description = "标签表")
@Table(name = "fr_label")
public class FrLabel {
    /**
     * label ID
     */
    @ApiModelProperty(hidden=true)
    @Id
    @Column(name = "label_id")
    private Integer labelId;

    /**
     * user ID
     */
    @ApiModelProperty(hidden=true)
    @Column(name = "user_id")
    private String userId;

    /**
     * label content
     */
    @ApiModelProperty(value = "标签内容", name = "label content", example = "General", required = true)
    @Column(name = "label_content")
    private String labelContent;

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

    /**
     * 获取user ID
     *
     * @return user_id - user ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置user ID
     *
     * @param userId user ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取label content
     *
     * @return label_content - label content
     */
    public String getLabelContent() {
        return labelContent;
    }

    /**
     * 设置label content
     *
     * @param labelContent label content
     */
    public void setLabelContent(String labelContent) {
        this.labelContent = labelContent;
    }
}