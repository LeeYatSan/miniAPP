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