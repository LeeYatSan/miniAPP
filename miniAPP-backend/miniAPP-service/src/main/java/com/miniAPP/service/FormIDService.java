package com.miniAPP.service;

public interface FormIDService {

    /**
     * @Description: 添加form ID
     * @param userID
     * @param formID
     */
    void addFormID(Long userID, String formID);

    /**
     * @Description 获取form ID
     * @param userID
     */
    String getFormid(Long userID);
}
