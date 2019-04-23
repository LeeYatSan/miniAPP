package com.miniAPP.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "fr_card")
public class FrCard {
    /**
     * card ID
     */
    @Id
    @Column(name = "card_id")
    private Long cardId;

    /**
     * user ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * creare time
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * last remember time
     */
    @Column(name = "last_remember_time")
    private Date lastRememberTime;

    /**
     * next pushing time
     */
    @Column(name = "next_time")
    private Date nextTime;

    /**
     * remember time counter and when it is not a positive number, it means user forget it
     */
    @Column(name = "remember_times")
    private Integer rememberTimes;

    /**
     * title
     */
    private String title;

    /**
     * the number of label: no more than 3
     */
    @Column(name = "label_num")
    private Integer labelNum;

    /**
     * content
     */
    private String content;

    /**
     * picture url
     */
    @Column(name = "pic_url")
    private String picUrl;

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
     * 获取last remember time
     *
     * @return last_remember_time - last remember time
     */
    public Date getLastRememberTime() {
        return lastRememberTime;
    }

    /**
     * 设置last remember time
     *
     * @param lastRememberTime last remember time
     */
    public void setLastRememberTime(Date lastRememberTime) {
        this.lastRememberTime = lastRememberTime;
    }

    /**
     * 获取next pushing time
     *
     * @return next_time - next pushing time
     */
    public Date getNextTime() {
        return nextTime;
    }

    /**
     * 设置next pushing time
     *
     * @param nextTime next pushing time
     */
    public void setNextTime(Date nextTime) {
        this.nextTime = nextTime;
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