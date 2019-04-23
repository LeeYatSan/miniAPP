package com.miniAPP.mapper;

import com.miniAPP.pojo.FrIdStub;
import com.miniAPP.utils.MyMapper;
import org.springframework.stereotype.Component;

@Component(value = "FrIdStubMapper")
public interface FrIdStubMapper extends MyMapper<FrIdStub> {

    /**
     * Generate a new user id
     *
     * @param stub
     */
    int generateID(int stub);

    /**
     * Get user id
     */
    Long getUserID();

}