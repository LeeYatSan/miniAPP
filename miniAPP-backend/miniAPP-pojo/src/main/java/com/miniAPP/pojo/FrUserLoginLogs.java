package com.miniAPP.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "fr_user_login_logs")
public class FrUserLoginLogs {
    /**
     * login log ID
     */
    @Id
    @Column(name = "login_log_id")
    private Integer loginLogId;

    /**
     * user ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * login time
     */
    @Column(name = "login_time")
    private Date loginTime;

    /**
     * 获取login log ID
     *
     * @return login_log_id - login log ID
     */
    public Integer getLoginLogId() {
        return loginLogId;
    }

    /**
     * 设置login log ID
     *
     * @param loginLogId login log ID
     */
    public void setLoginLogId(Integer loginLogId) {
        this.loginLogId = loginLogId;
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
     * 获取login time
     *
     * @return login_time - login time
     */
    public Date getLoginTime() {
        return loginTime;
    }

    /**
     * 设置login time
     *
     * @param loginTime login time
     */
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }
}