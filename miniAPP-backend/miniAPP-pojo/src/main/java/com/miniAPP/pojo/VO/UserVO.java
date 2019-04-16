package com.miniAPP.pojo.VO;


public class UserVO {

    /**
     * User Felis-Recall ID
     */
    private String ID;

    /**
     * User state
     */
    private Integer state;

    /**
     * User Total Login Days
     */
    private Integer loginDays;

    /**
     * User Cards Number
     */
    private Integer totalCards;

    /**
     * User Forgetting Cards Number
     */
    private Integer forgetCards;



    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getLoginDays() {
        return loginDays;
    }

    public void setLoginDays(Integer loginDays) {
        this.loginDays = loginDays;
    }

    public Integer getTotalCards() {
        return totalCards;
    }

    public void setTotalCards(Integer totalCards) {
        this.totalCards = totalCards;
    }

    public Integer getForgetCards() {
        return forgetCards;
    }

    public void setForgetCards(Integer forgetCards) {
        this.forgetCards = forgetCards;
    }
}
