package com.miniAPP.pojo;

import javax.persistence.*;

@Table(name = "fr_user_info")
public class FrUserInfo {
    /**
     * user ID
     */
    @Id
    @Column(name = "user_id")
    private Long userId;

    /**
     * login days
     */
    @Column(name = "login_days")
    private Integer loginDays;

    /**
     * total cards number
     */
    @Column(name = "total_cards")
    private Integer totalCards;


    /**
     * push frequency
     */
    @Column(name = "push_frequency")
    private Byte pushFrequency;

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
     * 获取login days
     *
     * @return login_days - login days
     */
    public Integer getLoginDays() {
        return loginDays;
    }

    /**
     * 设置login days
     *
     * @param loginDays login days
     */
    public void setLoginDays(Integer loginDays) {
        this.loginDays = loginDays;
    }

    /**
     * 获取total cards number
     *
     * @return total_cards - total cards number
     */
    public Integer getTotalCards() {
        return totalCards;
    }

    /**
     * 设置total cards number
     *
     * @param totalCards total cards number
     */
    public void setTotalCards(Integer totalCards) {
        this.totalCards = totalCards;
    }

    /**
     * 获取push frequency
     *
     * @return push_frequency - push frequency
     */
    public Byte getPushFrequency() {
        return pushFrequency;
    }

    /**
     * 设置push frequency
     *
     * @param pushFrequency push frequency
     */
    public void setPushFrequency(Byte pushFrequency) {
        this.pushFrequency = pushFrequency;
    }
}