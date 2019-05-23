package com.miniAPP.mapper;

import com.miniAPP.pojo.FrLabel;
import com.miniAPP.utils.MyMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "FrLabelMapper")
public interface FrLabelMapper extends MyMapper<FrLabel> {

    /**
     * 获取当前卡片所有标签
     *
     * @param cardID
     */
    List<String> queryCardAllLabel(Long cardID);

    /**
     * 获取当前用户所有标签
     *
     * @param userID
     */
    List<String> queryUserAllLabel(Long userID);

    /**
     * 获取标签ID
     *
     * @param userID
     */
    Integer queryLabelID(Long userID, String labelContent);
}