package com.miniAPP.pojo.VO;


import io.swagger.annotations.ApiModel;

@ApiModel(value = "用户对象", description = "后台返回的用户信息")
public class UserVO {

    /**
     * User Felis-Recall ID
     */
    private String ID;

    /**
     * User Session Token
     */
    private String SessionToken;

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

    public String getSessionToken() { return SessionToken; }

    public void setSessionToken(String sessionToken) { SessionToken = sessionToken; }

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
