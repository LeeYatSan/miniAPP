package com.miniAPP.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import javax.persistence.*;

@ApiModel(value = "Card", description = "用户卡片表")
@Table(name = "fr_card")
public class FrCard {
    /**
     * card ID
     */
    @ApiModelProperty(hidden=true)
    @Id
    @Column(name = "card_id")
    private String cardId;

    /**
     * user ID
     */
    @ApiModelProperty(hidden=true)
    @Column(name = "user_id")
    private String userId;

    /**
     * creare time
     */
    @ApiModelProperty(hidden=true)
    @Column(name = "create_time")
    private Date createTime;

    /**
     * least modified time
     */
    @ApiModelProperty(hidden=true)
    @Column(name = "modified_time")
    private Date modifiedTime;

    /**
     * remember time counter and when it is not a positive number, it means user forget it
     */
    @ApiModelProperty(hidden=true)
    @Column(name = "remember_times")
    private Integer rememberTimes;

    /**
     * title
     */
    @ApiModelProperty(value="标题", name="title", example="Felis Recall Example")
    @Column(name = "title")
    private String title;

    /**
     * the number of label: no more than 3
     */
    @ApiModelProperty(hidden=true)
    @Column(name = "label_num")
    private Integer labelNum;

    /**
     * content
     */
    @ApiModelProperty(value="内容", name="content", example="(Please Input the text)")
    @Column(name = "content")
    private String content;

    /**
     * picture url
     */
    @ApiModelProperty(value="图片URL", name="picture URL", example="https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png")
    @Column(name = "pic_url")
    private String picUrl;

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
     * 获取creare time
     *
     * @return create_time - creare time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置creare time
     *
     * @param createTime creare time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取least modified time
     *
     * @return modified_time - least modified time
     */
    public Date getModifiedTime() {
        return modifiedTime;
    }

    /**
     * 设置least modified time
     *
     * @param modifiedTime least modified time
     */
    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    /**
     * 获取remember time counter and when it is not a positive number, it means user forget it
     *
     * @return remember_times - remember time counter and when it is not a positive number, it means user forget it
     */
    public Integer getRememberTimes() {
        return rememberTimes;
    }

    /**
     * 设置remember time counter and when it is not a positive number, it means user forget it
     *
     * @param rememberTimes remember time counter and when it is not a positive number, it means user forget it
     */
    public void setRememberTimes(Integer rememberTimes) {
        this.rememberTimes = rememberTimes;
    }

    /**
     * 获取title
     *
     * @return title - title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置title
     *
     * @param title title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取the number of label: no more than 3
     *
     * @return label_num - the number of label: no more than 3
     */
    public Integer getLabelNum() {
        return labelNum;
    }

    /**
     * 设置the number of label: no more than 3
     *
     * @param labelNum the number of label: no more than 3
     */
    public void setLabelNum(Integer labelNum) {
        this.labelNum = labelNum;
    }

    /**
     * 获取content
     *
     * @return content - content
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置content
     *
     * @param content content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取picture url
     *
     * @return pic_url - picture url
     */
    public String getPicUrl() {
        return picUrl;
    }

    /**
     * 设置picture url
     *
     * @param picUrl picture url
     */
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}