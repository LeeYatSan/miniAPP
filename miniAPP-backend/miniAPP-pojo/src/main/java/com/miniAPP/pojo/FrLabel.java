package com.miniAPP.pojo;

import javax.persistence.*;

@Table(name = "fr_label")
public class FrLabel {
    /**
     * label ID
     */
    @Id
    @Column(name = "label_id")
    private Integer labelId;

    /**
     * user ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * label content
     */
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
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置user ID
     *
     * @param userId user ID
     */
    public void setUserId(Long userId) {
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