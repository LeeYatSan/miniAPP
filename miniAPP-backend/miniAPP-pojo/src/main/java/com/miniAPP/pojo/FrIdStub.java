package com.miniAPP.pojo;

import javax.persistence.*;

@Table(name = "fr_id_stub")
public class FrIdStub {
    /**
     * user ID
     */
    @Id
    private Long id;

    /**
     * stubNO.
     */
    private Integer stub;

    /**
     * 获取user ID
     *
     * @return id - user ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置user ID
     *
     * @param id user ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取stubNO.
     *
     * @return stub - stubNO.
     */
    public Integer getStub() {
        return stub;
    }

    /**
     * 设置stubNO.
     *
     * @param stub stubNO.
     */
    public void setStub(Integer stub) {
        this.stub = stub;
    }
}