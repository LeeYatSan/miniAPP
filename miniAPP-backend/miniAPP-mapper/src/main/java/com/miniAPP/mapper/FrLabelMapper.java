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
}