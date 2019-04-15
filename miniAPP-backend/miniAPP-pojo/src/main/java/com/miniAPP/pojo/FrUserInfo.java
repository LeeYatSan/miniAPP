package com.miniAPP.pojo;

import javax.persistence.*;

@Table(name = "fr_user_info")
public class FrUserInfo {
    /**
     * user ID
     */
    @Id
    @Column(name = "user_id")
    private String userId;

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
     * forgeting cards number
     */
    @Column(name = "forget_cards")
    private Integer forgetCards;

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
     * 获取forgeting cards number
     *
     * @return forget_cards - forgeting cards number
     */
    public Integer getForgetCards() {
        return forgetCards;
    }

    /**
     * 设置forgeting cards number
     *
     * @param forgetCards forgeting cards number
     */
    public void setForgetCards(Integer forgetCards) {
        this.forgetCards = forgetCards;
    }
}