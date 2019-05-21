package com.miniAPP.service;


import com.miniAPP.pojo.FrUserFormid;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;

public interface FormIDService {

    /**
     * @Description 添加form ID
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
