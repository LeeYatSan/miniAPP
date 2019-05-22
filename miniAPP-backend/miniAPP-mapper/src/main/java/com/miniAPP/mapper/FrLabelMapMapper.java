package com.miniAPP.mapper;

import com.miniAPP.pojo.FrLabelMap;
import com.miniAPP.utils.MyMapper;
import org.springframework.stereotype.Component;


@Component(value = "FrLabelMapMapper")
public interface FrLabelMapMapper extends MyMapper<FrLabelMap> {

    /**
     * 根据cardID删除该卡所有标签映射
     *
     * @param cardID
     */
    void deleteCardAllLabel(Long cardID);
}