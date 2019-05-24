package com.miniAPP.mapper;

import com.miniAPP.pojo.FrCard;
import com.miniAPP.utils.MyMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "FrCardMapper")
public interface FrCardMapper extends MyMapper<FrCard> {

    /**
     * Get all familiar cards
     *
     * @param userID
     */
    List<FrCard> getAllFamiliarCards(Long userID);


    /**
     * Get familiar cards number
     *
     * @param userID
     */
    int queryFamiliarCardNum(Long userID);


    /**
     * Get the users who need to be noticed
     *
     */
    List<Long> queryNoticedUserList();


    /**
     * Get unfamiliar cards
     *
     * @param userID
     */
    List<FrCard> queryUnFamiliarCard(Long userID);
}