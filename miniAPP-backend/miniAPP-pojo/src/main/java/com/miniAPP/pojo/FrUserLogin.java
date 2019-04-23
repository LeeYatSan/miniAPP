package com.miniAPP.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "fr_user_login")
public class FrUserLogin {
    /**
     * user ID
     */
    @Id
    @Column(name = "user_id")
    private Long userId;

    /**
     * user WeChar openid
     */
    @Column(name = "user_openid")
    private String userOpenid;

    /**
     * user state: 0 -> offline  1 -> online
     */
    @Column(name = "user_state")
    private Boolean userState;

    /**
     * last login time
     */
    @Column(name = "last_login_time")
    private Date lastLoginTime;

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
     * 获取user WeChar openid
     *
     * @return user_openid - user WeChar openid
     */
    public String getUserOpenid() {
        return userOpenid;
    }

    /**
     * 设置user WeChar openid
     *
     * @param userOpenid user WeChar openid
     */
    public void setUserOpenid(String userOpenid) {
        this.userOpenid = userOpenid;
    }

    /**
     * 获取user state: 0 -> offline  1 -> online
     *
     * @return user_state - user state: 0 -> offline  1 -> online
     */
    public Boolean getUserState() {
        return userState;
    }

    /**
     * 设置user state: 0 -> offline  1 -> online
     *
     * @param userState user state: 0 -> offline  1 -> online
     */
    public void setUserState(Boolean userState) {
        this.userState = userState;
    }

    /**
     * 获取last login time
     *
     * @return last_login_time - last login time
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * 设置last login time
     *
     * @param lastLoginTime last login time
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}