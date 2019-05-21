package com.miniAPP.pojo;

import org.springframework.context.annotation.Bean;

import java.util.Date;
import javax.persistence.*;

@Table(name = "fr_user_formid")
public class FrUserFormid {
    /**
     * user ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * user WeChar openid
     */
    @Column(name = "user_openid")
    private String userOpenid;

    /**
     * form ID
     */
    @Column(name = "form_id")
    private String formId;

    /**
     * get time
     */
    @Column(name = "get_time")
    private Date getTime;

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
     * 获取form ID
     *
     * @return form_id - form ID
     */
    public String getFormId() {
        return formId;
    }

    /**
     * 设置form ID
     *
     * @param formId form ID
     */
    public void setFormId(String formId) {
        this.formId = formId;
    }

    /**
     * 获取get time
     *
     * @return get_time - get time
     */
    public Date getGetTime() {
        return getTime;
    }

    /**
     * 设置get time
     *
     * @param getTime get time
     */
    public void setGetTime(Date getTime) {
        this.getTime = getTime;
    }
}