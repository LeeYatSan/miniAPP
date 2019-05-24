package com.miniAPP.service.impl;

import com.miniAPP.mapper.FrCardMapper;
import com.miniAPP.mapper.FrLabelMapMapper;
import com.miniAPP.mapper.FrLabelMapper;
import com.miniAPP.mapper.FrUserInfoMapper;
import com.miniAPP.pojo.FrCard;
import com.miniAPP.pojo.FrLabel;
import com.miniAPP.pojo.FrLabelMap;
import com.miniAPP.pojo.FrUserInfo;
import com.miniAPP.pojo.VO.CardNumDetailVO;
import com.miniAPP.pojo.VO.CardVO;
import com.miniAPP.service.CardService;
import com.miniAPP.utils.JSONResult;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.ocr.v20181119.OcrClient;
import com.tencentcloudapi.ocr.v20181119.models.GeneralFastOCRRequest;
import com.tencentcloudapi.ocr.v20181119.models.GeneralFastOCRResponse;
import com.tencentcloudapi.ocr.v20181119.models.TextDetection;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private FrCardMapper cardMapper;

    @Autowired
    private FrLabelMapper labelMapper;

    @Autowired
    private FrLabelMapMapper labelMapMapper;

    @Autowired
    private FrUserInfoMapper userInfoMapper;

    private static final long forgettingCurve[] = {
            0,                  // 0->创建日
            1*24*60*60*1000L,	// 1->1天
            2*24*60*60*1000L,	// 2->2天
            4*24*60*60*1000L,	// 3->4天
            7*24*60*60*1000L,	// 4->7天
            15*24*60*60*1000L,	// 5->15天
            31*24*60*60*1000L	// 6->31天
                                // 7->熟记
    };

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Long saveCard(FrCard card){

        Long userID = card.getUserId();
        FrUserInfo userInfo = userInfoMapper.selectByPrimaryKey(userID);
        StringBuilder cardIDstr = new StringBuilder(userID.toString());
        cardIDstr.append(String.format("%05d", userInfo.getTotalCards()));
        Long cardId = Long.parseLong(cardIDstr.toString());

        card.setCardId(cardId);
        card.setRememberTimes(0);
        card.setMemoLevel((byte)0);
        Date now = new Date(System.currentTimeMillis());
        card.setLastRememberTime(now);
        card.setCreateTime(now);
        card = nextTime(card, false);

        userInfo.setTotalCards(userInfo.getTotalCards()+1);

        cardMapper.insert(card);
        userInfoMapper.updateByPrimaryKeySelective(userInfo);

        return card.getCardId();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void editCard(FrCard card){
        cardMapper.updateByPrimaryKeySelective(card);
        labelMapMapper.deleteByPrimaryKey(card.getCardId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void delCard(Long userID, Long cardID){

        //删除卡片
        cardMapper.deleteByPrimaryKey(cardID);

        //获取该卡片所有标签映射
        FrLabelMap frLabelMap = new FrLabelMap();
        frLabelMap.setCardId(cardID);
        List<FrLabelMap>frLabelMaps = labelMapMapper.select(frLabelMap);

        //无卡片标签映射则直接结束
        if(frLabelMaps == null || frLabelMaps.size() == 0){
            return;
        }
        //删除卡片映射前先检查该标签是否仅剩1张即被删除的本张卡片，若是，则顺便删除该标签
        for(FrLabelMap curr : frLabelMaps){
            int currLabelID = curr.getLabelId();
            FrLabelMap temp = new FrLabelMap();
            temp.setLabelId(currLabelID);
            List<FrLabelMap>labelMaps = labelMapMapper.select(temp);
            if(labelMaps != null && labelMaps.size() == 1){
                labelMapper.deleteByPrimaryKey(currLabelID);
            }
        }
        //删除卡片标签映射
        labelMapMapper.deleteCardAllLabel(cardID);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveLabel(Long userID, Long cardID, String[] labelContents){
        FrLabel label=new FrLabel();
        FrLabelMap labelMap=new FrLabelMap();

        label.setUserId(userID);
        labelMap.setCardId(cardID);

        if(labelContents.length==0){
            label.setLabelContent("Genaral");
            initLabel(label, labelMap);
        }
        else {
            for (String s : labelContents) {
                label.setLabelContent(s);
                initLabel(label, labelMap);
            }
        }
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void initLabel(FrLabel label, FrLabelMap labelMap){

        FrLabel temp = labelMapper.selectOne(label);
        if(temp == null){
            labelMapper.insert(label);
            label = labelMapper.selectOne(label);
        }
        else{
            label = temp;
        }
        labelMap.setLabelId(label.getLabelId());
        labelMapMapper.insert(labelMap);
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean deleteLabel(Long userID, String labelContent){

        FrLabel label = new FrLabel();
        label.setUserId(userID);
        label.setLabelContent(labelContent);
        label = labelMapper.selectOne(label);
        if(label == null){
            return false;
        }
        Integer labelID = label.getLabelId();
        FrLabelMap frLabelMap = new FrLabelMap();
        frLabelMap.setLabelId(labelID);

        //如果该标签下没有卡片了则可以删除该标签
        List<FrLabelMap>labelMaps = labelMapMapper.select(frLabelMap);
        if(labelMaps != null && labelMaps.size() == 0){
            labelMapper.deleteByPrimaryKey(labelID);
            return true;
        }
        else{
            return false;
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<FrCard> queryCardByUserID(Long userID){

        FrCard card=new FrCard();
        card.setUserId(userID);
       return cardMapper.select(card);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public FrCard queryCardByCardID(Long cardID){

        FrCard card = new FrCard();
        card.setCardId(cardID);
        return cardMapper.selectOne(card);
    }

    /**
     *
     * @param frCard 当前卡片
     * @param forget 是否忘记：false：没忘记 true：忘记
     * @return FrCard
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public FrCard nextTime(FrCard frCard, boolean forget){

        byte currMemoLevel = frCard.getMemoLevel();
        if(!forget){
            if(currMemoLevel < 6){
                currMemoLevel = (byte)(currMemoLevel+1);
                frCard.setMemoLevel(currMemoLevel);
                frCard.setNextTime(new Date(System.currentTimeMillis() + forgettingCurve[currMemoLevel]));
                cardMapper.updateByPrimaryKeySelective(frCard);
            }
            else if(currMemoLevel == 6){
                frCard.setMemoLevel((byte)(currMemoLevel+1));
                cardMapper.updateByPrimaryKeySelective(frCard);
            }
        }
        else {
            currMemoLevel = (byte)1;
            frCard.setMemoLevel(currMemoLevel);
            frCard.setNextTime(new Date(System.currentTimeMillis() + forgettingCurve[currMemoLevel]));
            cardMapper.updateByPrimaryKeySelective(frCard);
        }
        frCard.setRememberTimes(frCard.getRememberTimes()+1);
        frCard.setLastRememberTime(new Date(System.currentTimeMillis()));
        return frCard;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Long> queryUserNeededToBeNoticed(){

        return cardMapper.queryNoticedUserList();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public CardNumDetailVO queryCardNumDetail(Long userID){

        return new CardNumDetailVO(userID, userInfoMapper.selectByPrimaryKey(userID).getTotalCards(),
                                                            cardMapper.queryFamiliarCardNum(userID));
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public int queryUnfamiliarCardNum(Long userID){

        int totalCardNum = userInfoMapper.selectByPrimaryKey(userID).getTotalCards();
        int familiarCardNum = cardMapper.queryFamiliarCardNum(userID);
        return totalCardNum-familiarCardNum;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<FrCard> getUnFamiliarCard(Long userID){

        return cardMapper.queryUnFamiliarCard(userID);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public CardVO getCardLabels(FrCard card){

        CardVO cardVO = new CardVO();
        cardVO.setCard(card);
        cardVO.setLabels(labelMapper.queryCardAllLabel(card.getCardId()).toArray());
        return cardVO;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Object[] getEachCardLabels(List<FrCard> cards){

        ArrayList<CardVO> cardVOs = new ArrayList<>();
        for(FrCard card : cards){
            cardVOs.add(getCardLabels(card));
        }
        return cardVOs.toArray();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public JSONResult Ocr(String picUrl){
        try{
            Credential cred = new Credential("AKIDJZOXdbJNfj1vb2uJ8dwGO0Vi5Iy5vlL1", "Shja5LVkneQPnhvwV6xe9RmFa2dfprBo");
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("ocr.tencentcloudapi.com");
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            OcrClient client = new OcrClient(cred, "ap-guangzhou", clientProfile);
            String params = "{\"ImageUrl\":\""+picUrl+"\"}";
            GeneralFastOCRRequest req = GeneralFastOCRRequest.fromJsonString(params, GeneralFastOCRRequest.class);
            GeneralFastOCRResponse resp = client.GeneralFastOCR(req);

            TextDetection[] textDetections=resp.getTextDetections();
            String text="";
            for(int i=0;i<textDetections.length;i++){
                if(i>0 && textDetections[i].getAdvancedInfo().compareTo(textDetections[i-1].getAdvancedInfo())!=0) text+="\n";
                text+=textDetections[i].getDetectedText();
            }
            text = StringEscapeUtils.escapeJava(text);
            return JSONResult.ok(text);

        } catch (TencentCloudSDKException e) {
            return JSONResult.errorMsg(e.toString());
        }
    }
}
