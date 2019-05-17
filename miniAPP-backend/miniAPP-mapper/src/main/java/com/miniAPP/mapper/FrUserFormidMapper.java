package com.miniAPP.mapper;

import com.miniAPP.pojo.FrUserFormid;
import com.miniAPP.utils.MyMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "FrUserFormid")
public interface FrUserFormidMapper extends MyMapper<FrUserFormid> {

    /**
     * Get formID by userID
     *
     * @param userID
     */
    List<FrUserFormid> getFormIDListByUserID(Long userID);
}