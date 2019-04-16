package com.miniAPP.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import javax.persistence.*;

@ApiModel(value = "UserRegister", description = "用户注册信息表")
@Table(name = "fr_user_register_info")
public class FrUserRegisterInfo {
    /**
     * user ID
     */
    @ApiModelProperty(hidden=true)
    @Id
    @Column(name = "user_id")
    private String userId;

    /**
     * register time
     */
    @ApiModelProperty(hidden=true)
    @Column(name = "register_time")
    private Date registerTime;

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
     * 获取register time
     *
     * @return register_time - register time
     */
    public Date getRegisterTime() {
        return registerTime;
    }

    /**
     * 设置register time
     *
     * @param registerTime register time
     */
    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }
}