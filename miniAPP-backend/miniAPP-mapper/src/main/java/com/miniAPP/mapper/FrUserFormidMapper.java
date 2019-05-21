package com.miniAPP.mapper;

import com.miniAPP.pojo.FrUserFormid;
import com.miniAPP.utils.MyMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "FrUserFormidMapper")
public interface FrUserFormidMapper extends MyMapper<FrUserFormid> {

    /**
     * Get formID by userId
     *
     * @param userId
     */
    List<FrUserFormid> getFormIDListByUserID(Long userId);

    /**
     * Delete a formId
     *
     * @param formId
     */
    void deleteFormid(String formId);
}