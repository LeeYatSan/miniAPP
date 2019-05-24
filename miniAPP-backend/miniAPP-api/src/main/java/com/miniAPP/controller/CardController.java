package com.miniAPP.controller;

import com.miniAPP.mapper.FrCardMapper;
import com.miniAPP.mapper.FrLabelMapMapper;
import com.miniAPP.mapper.FrLabelMapper;
import com.miniAPP.pojo.FrCard;
import com.miniAPP.pojo.FrLabel;
import com.miniAPP.service.CardService;
import com.miniAPP.service.FormIDService;
import com.miniAPP.utils.JSONResult;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@Api(value = "卡片API", tags = {"Card Controller"})
public class CardController extends BasicController {

    @Autowired
    private FrCardMapper cardMapper;

    @Autowired
    private FrLabelMapper labelMapper;

    @Autowired
    private FrLabelMapMapper labelMapMapper;

    @Autowired
    private CardService cardService;

    @Autowired
    private FormIDService formIDService;


    @ApiOperation(value = "获取用户的所有卡片", notes = "获取用户的所有卡片：通过指定用户ID")
    @ApiImplicitParams({@ApiImplicitParam(name = "userID", value = "userID", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "sessionToken", value = "sessionToken", required = true, dataType = "String", paramType = "query")})
    @ApiResponses({ @ApiResponse(code = 502, message = "Invalid Session Token"), @ApiResponse(code = 200, message = "ok") })
    @PostMapping("/getAllCardsByUserID")
    public JSONResult getAllCardsByUserID(Long userID, String sessionToken){
        if(userID == null || userID <= 0){
            return JSONResult.errorMsg(PARAM_MISSING+"userID");
        }if(sessionToken == null || sessionToken.equals("")){
            return JSONResult.errorMsg(PARAM_MISSING+"sessionToken");
        }
        if(!sessionTokenIsValid(userID, sessionToken)){
            return JSONResult.errorTokenMsg(INVALID_SESSION_TOKEN);
        }

        return JSONResult.ok(cardService.getEachCardLabels(cardService.queryCardByUserID(userID)));
    }

    @ApiOperation(value = "获取用户的所有标签", notes = "获取用户的所有标签：通过指定用户ID")
    @ApiImplicitParams({@ApiImplicitParam(name = "userID", value = "userID", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "sessionToken", value = "sessionToken", required = true, dataType = "String", paramType = "query")})
    @ApiResponses({ @ApiResponse(code = 502, message = "Invalid Session Token"), @ApiResponse(code = 200, message = "ok") })
    @PostMapping("/getAllLabelsByUserID")
    public JSONResult getAllLabelsByUserID(Long userID, String sessionToken){
        if(!sessionTokenIsValid(userID, sessionToken)){
            return JSONResult.errorTokenMsg(INVALID_SESSION_TOKEN);
        }
        FrLabel label=new FrLabel();
        label.setUserId(userID);
        List<String> reuslt = labelMapper.queryUserAllLabel(userID);
        return JSONResult.ok(reuslt.toArray());
    }

    @ApiOperation(value = "获取用户的所有卡片", notes = "获取用户的所有卡片：通过指定卡片标签")
    @ApiImplicitParams({@ApiImplicitParam(name = "userID", value = "userID", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "labelContent", value = "labelContent", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sessionToken", value = "sessionToken", required = true, dataType = "String", paramType = "query")})
    @ApiResponses({ @ApiResponse(code = 502, message = "Invalid Session Token"), @ApiResponse(code = 200, message = "ok") })
    @PostMapping("/getAllCardsByLabel")
    public JSONResult getAllCardsByLabel(Long userID, String sessionToken, String labelContent){
        if(userID == null || userID <= 0){
            return JSONResult.errorMsg(PARAM_MISSING+"userID");
        }if(sessionToken == null || sessionToken.equals("")){
            return JSONResult.errorMsg(PARAM_MISSING+"sessionToken");
        }
        if(!sessionTokenIsValid(userID, sessionToken)){
            return JSONResult.errorTokenMsg(INVALID_SESSION_TOKEN);
        }
        if(StringUtils.isBlank(labelContent)){
            labelContent="Genaral";
        }
//        FrLabel label=new FrLabel();
//        label.setUserId(userID);
//        label.setLabelContent(labelContent);
//
//        //得到符合要求的标签ID。通过用户ID和标签内容。fr_label(label_id, user_id, label_content)
//        List<FrLabel> labels=labelMapper.select(label); //存放用户所有的标签ID
//
//        //得到符合要求的卡片ID。通过标签ID。fr_label_Map(label_map_id, card_id, label_id)
//        FrLabelMap labelMap=new FrLabelMap();
//        List<Long> cardIDs=new ArrayList<>(); //存放符合要求的所有卡片ID
//        for(FrLabel l: labels) {
//            labelMap.setLabelId(l.getLabelId());
//            List<FrLabelMap> labelMaps=labelMapMapper.select(labelMap);
//            cardIDs.add(labelMaps.get(0).getCardId());//注意：一个标签ID对应一张卡片ID
//        }
//
//        //得到符合要求的卡片。通过卡片ID。fr_card(...)
//        FrCard card=new FrCard();
//        List<FrCard> cards=new ArrayList<>(); //存放符合要求的用户的所有卡片
//        for(Long s: cardIDs){
//            card.setCardId(s);
//            cards.addAll(cardMapper.select(card));
//        }

        //如果是"#All"标签，返回全部卡片
        if(labelContent.equals("#All")){
            return JSONResult.ok(cardService.getEachCardLabels(cardService.queryCardByUserID(userID)));
        }
        //否则返回具体标签下的卡片
        int labelID=labelMapper.queryLabelID(userID, labelContent);
        List<FrCard> cards=cardMapper.queryAllCardByLabelID(labelID);
        return JSONResult.ok(cardService.getEachCardLabels(cards));
    }

    @ApiOperation(value = "获取某卡片的所有标签", notes = "获取某卡片的所有标签：通过指定卡片ID")
    @ApiImplicitParams({@ApiImplicitParam(name = "userID", value = "userID", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "cardID", value = "cardID", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "sessionToken", value = "sessionToken", required = true, dataType = "String", paramType = "query")})
    @ApiResponses({ @ApiResponse(code = 502, message = "Invalid Session Token"), @ApiResponse(code = 200, message = "ok") })
    @PostMapping("/getAllLabelsByCardID")
    public JSONResult getAllLabelsByCardID(Long userID, Long cardID, String sessionToken){
        if(!sessionTokenIsValid(userID, sessionToken)){
            return JSONResult.errorTokenMsg(INVALID_SESSION_TOKEN);
        }

        if(cardID==null || cardID==0){
            return JSONResult.errorMsg(PARAM_MISSING+"cardID");
        }

        return JSONResult.ok(cardService.getCardLabels(cardService.queryCardByCardID(cardID)));
    }

    @ApiOperation(value = "修改卡片", notes = "修改卡片：如有多个标签，以空格分割")
    @ApiImplicitParams({@ApiImplicitParam(name = "userID", value = "userID", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "card", value = "card", required = true, dataType = "FrCard", paramType = "query"),
            @ApiImplicitParam(name = "labelContent", value = "labelContent", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sessionToken", value = "sessionToken", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "formID", value = "formID", required = true, dataType = "String", paramType = "query")})
    @ApiResponses({ @ApiResponse(code = 502, message = "Invalid Session Token"), @ApiResponse(code = 200, message = "ok") })
    @PostMapping("/editCard")
    public JSONResult editCard(Long userID, FrCard card, String labelContent, String sessionToken,  String formID){
        if(!sessionTokenIsValid(userID, sessionToken)){
            return JSONResult.errorTokenMsg(INVALID_SESSION_TOKEN);
        }

        if(formID != null)
            formIDService.addFormID(userID, formID);

        if(card.getCardId()==null || card.getCardId()==0){
            return JSONResult.errorMsg(PARAM_MISSING+"cardID");
        }

        if(StringUtils.isBlank(card.getTitle())){
            return JSONResult.errorMsg(PARAM_MISSING+"title");
        }

        if(StringUtils.isBlank(card.getContent())){
            return JSONResult.errorMsg(PARAM_MISSING+"content");
        }

        if(StringUtils.isBlank(labelContent)){
            return JSONResult.errorMsg(PARAM_MISSING+"label content");
        }

        card.setUserId(userID);
        String[] labelContents=labelContent.split(" ");
        card.setLabelNum(labelContents.length);

        //存储标签
        cardService.saveLabel(userID ,card.getCardId(), labelContents);

        return JSONResult.ok(cardService.queryCardByCardID(card.getCardId()));
    }


    @ApiOperation(value = "删除卡片", notes = "删除卡片")
    @ApiImplicitParams({@ApiImplicitParam(name = "userID", value = "userID", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "cardID", value = "cardID", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "sessionToken", value = "sessionToken", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "formID", value = "formID", required = true, dataType = "String", paramType = "query")})
    @ApiResponses({ @ApiResponse(code = 502, message = "Invalid Session Token"), @ApiResponse(code = 200, message = "ok") })
    @PostMapping("/delCard")
    public JSONResult delCard(Long userID, Long cardID, String sessionToken, String formID){
        if(!sessionTokenIsValid(userID, sessionToken)){
            return JSONResult.errorTokenMsg(INVALID_SESSION_TOKEN);
        }

        if(formID != null)
            formIDService.addFormID(userID, formID);

        if(cardID==null || cardID==0){
            return JSONResult.errorMsg("卡片ID为空");
        }

        cardService.delCard(userID, cardID);
        return JSONResult.ok();
    }


    @ApiOperation(value = "记住/忘记卡片", notes = "记住/忘记卡片")
    @ApiImplicitParams({@ApiImplicitParam(name = "userID", value = "userID", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "sessionToken", value = "sessionToken", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "cardID", value = "cardID", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "remember", value = "remember", required = true, dataType = "boolean", paramType = "query")})
    @ApiResponses({ @ApiResponse(code = 502, message = "Invalid Session Token"),
                    @ApiResponse(code = 502, message = "Parameter missing"),
                    @ApiResponse(code = 200, message = "ok") })
    @PostMapping("/rememberCardOrNot")
    public JSONResult rememberCard(Long userID, String sessionToken, Long cardID, boolean remember){
        if(!sessionTokenIsValid(userID, sessionToken)){
            return JSONResult.errorTokenMsg(INVALID_SESSION_TOKEN);
        }
        if(cardID == null){
            return JSONResult.errorMsg(PARAM_MISSING+"cardID");
        }
        FrCard card = cardService.queryCardByCardID(cardID);
        card = cardService.nextTime(card, remember);
        return JSONResult.ok(cardService.getCardLabels(card));
    }

    @ApiOperation(value = "熟记卡片", notes = "获取熟记卡片")
    @ApiImplicitParams({@ApiImplicitParam(name = "userID", value = "userID", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "sessionToken", value = "sessionToken", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "cardID", value = "cardID", required = true, dataType = "Long", paramType = "query")})
    @ApiResponses({ @ApiResponse(code = 502, message = "Invalid Session Token"),
                    @ApiResponse(code = 200, message = "ok") })
    @PostMapping("/getAllFamiliarCards")
    public JSONResult getAllFamiliarCards(Long userID, String sessionToken){
        if(!sessionTokenIsValid(userID, sessionToken)){
            return JSONResult.errorTokenMsg(INVALID_SESSION_TOKEN);
        }
        List<FrCard> cards = cardMapper.getAllFamiliarCards(userID);
        return JSONResult.ok(cards.toArray());
    }

    @ApiOperation(value = "熟记卡片数量", notes = "获取熟记卡片数量")
    @ApiImplicitParams({@ApiImplicitParam(name = "userID", value = "userID", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "sessionToken", value = "sessionToken", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "cardID", value = "cardID", required = true, dataType = "Long", paramType = "query")})
    @ApiResponses({ @ApiResponse(code = 502, message = "Invalid Session Token"),
            @ApiResponse(code = 200, message = "ok") })
    @PostMapping("/getFamiliarCardNum")
    public JSONResult getFamiliarCardNum(Long userID, String sessionToken){
        if(!sessionTokenIsValid(userID, sessionToken)){
            return JSONResult.errorTokenMsg(INVALID_SESSION_TOKEN);
        }
        return JSONResult.ok(cardMapper.queryFamiliarCardNum(userID));
    }


    @ApiOperation(value = "上传图片", notes = "上传图片")
    @ApiImplicitParams({@ApiImplicitParam(name = "userID", value = "userID", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "photoFile", value = "photoFile", required = true, dataType = "MultipartFile", paramType = "query"),
            @ApiImplicitParam(name = "ocr", value = "ocr", required = true, dataType = "boolean", paramType = "query"),
            @ApiImplicitParam(name = "sessionToken", value = "sessionToken", required = true, dataType = "String", paramType = "query")})
    @ApiResponses({ @ApiResponse(code = 502, message = "Invalid Session Token"), @ApiResponse(code = 200, message = "ok") })
    @PostMapping("/uploadPhoto")
    public JSONResult uploadPhoto(Long userID, @RequestParam("photo")MultipartFile photoFile, boolean ocr, String sessionToken){
        if(!sessionTokenIsValid(userID, sessionToken)){
            return JSONResult.errorTokenMsg(INVALID_SESSION_TOKEN);
        }

        if(photoFile!=null){
            String type;//文件类型
            String fileName=photoFile.getOriginalFilename(); //文件原名称
            String realFileName; //文件现名称
            String realDirPath = "/root/Documents/FelisRecall/images/"+ userID; //文件存放路径
            String realPath;

            if(!new File(realDirPath).exists())
                new File(realDirPath).mkdirs();

            //判断文件类型
            type= fileName.indexOf('.')!=-1 ? fileName.substring(fileName.lastIndexOf('.')+1, fileName.length()) : null;
            if(type!=null) {
                //PNG、JPG、JPEG图片格式，可后续添加
                if ("PNG".equals(type.toUpperCase()) || "JPG".equals(type.toUpperCase()) || "JPEG".equals(type.toUpperCase())) {
                    realFileName = System.currentTimeMillis() + "." + type.toLowerCase();
                    realPath=realDirPath+"/"+realFileName;
                    try {
                        photoFile.transferTo(new File(realPath));
                    } catch (IOException e) {
                        e.printStackTrace();
                        return JSONResult.errorMsg("图片上传失败");
                    }
                }else{
                    return JSONResult.errorMsg("不支持的文件类型");
                }

                //保存成功，接下来处理OCR
                if(!ocr)
                    return JSONResult.ok("{\"picUrl\":\""+realFileName+"\"}"); //此处直接返回xxxx.jpg，即图片文件名

                String jsonText="{\"picUrl\":\""+realFileName+"\"";
                JSONResult ocrResult=cardService.Ocr("http://134.175.11.69:8080/images/"+userID+"/"+realFileName);
                if(ocrResult.getStatus()!=200)
                    jsonText+=",\"ocrState\":"+ocrResult.getStatus()+",\"ocrMessage\":\""+ocrResult.getMsg()+"\"}";
                else jsonText+=",\"ocrState\":200,\"ocrText\":\""+ocrResult.getData()+"\"}";
                return JSONResult.ok(jsonText);

            }
            else {
                return JSONResult.errorMsg("不合法文件");
            }
        }
        return JSONResult.errorMsg("文件为空");
    }


    @ApiOperation(value = "获取未熟记的卡片", notes = "获取未熟记的卡片")
    @ApiImplicitParams({@ApiImplicitParam(name = "userID", value = "userID", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "sessionToken", value = "sessionToken", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "formID", value = "formID", required = true, dataType = "String", paramType = "query")})
    @ApiResponses({ @ApiResponse(code = 502, message = "Invalid Session Token"),
            @ApiResponse(code = 200, message = "ok") })
    @PostMapping("/getUnFamiliarCard")
    public JSONResult getFamiliarCard(Long userID, String sessionToken, String formID){

        if(!sessionTokenIsValid(userID, sessionToken)){
            return JSONResult.errorTokenMsg(INVALID_SESSION_TOKEN);
        }

        if(formID != null)
            formIDService.addFormID(userID, formID);

        List<FrCard> cards = cardService.getUnFamiliarCard(userID);

        return JSONResult.ok(cards.toArray());
    }

    @ApiOperation(value = "分享卡片", notes = "分享卡片")
    @ApiImplicitParams({@ApiImplicitParam(name = "userID", value = "userID", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "sessionToken", value = "sessionToken", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "formID", value = "formID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "cardID", value = "cardID", required = true, dataType = "Long", paramType = "query")})
    @ApiResponses({ @ApiResponse(code = 502, message = "Parameter Missing"),
            @ApiResponse(code = 200, message = "ok") })
    @PostMapping("/shareCard")
    public JSONResult shareCard(Long userID, String sessionToken, String formID, Long cardID){

        if(!sessionTokenIsValid(userID, sessionToken)){
            return JSONResult.errorTokenMsg(INVALID_SESSION_TOKEN);
        }

        if(formID != null){
            formIDService.addFormID(userID, formID);
        }

        if(cardID == null){
            return JSONResult.errorTokenMsg(PARAM_MISSING+"cardID");
        }

        FrCard card = cardService.queryCardByCardID(cardID);
        card.setUserId(userID);
        cardService.saveCard(card);

        return JSONResult.ok();
    }

    //需要formID
    @ApiOperation(value = "保存卡片", notes = "保存卡片：如有多个标签，以空格分割")
    @ApiImplicitParams({@ApiImplicitParam(name = "userID", value = "userID", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "card", value = "card", required = true, dataType = "FrCard", paramType = "query"),
            @ApiImplicitParam(name = "labelContent", value = "labelContent", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "photoFile", value = "photoFile", required = true, dataType = "MultipartFile", paramType = "query"),
            @ApiImplicitParam(name = "sessionToken", value = "sessionToken", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "formID", value = "formID", required = true, dataType = "String", paramType = "query")})
    @ApiResponses({ @ApiResponse(code = 502, message = "Invalid Session Token"), @ApiResponse(code = 200, message = "ok") })
    @PostMapping("/saveCard")
    public JSONResult saveCard(Long userID, FrCard card, String labelContent, String sessionToken, String formID) {
        if(!sessionTokenIsValid(userID, sessionToken)){
            return JSONResult.errorTokenMsg(INVALID_SESSION_TOKEN);
        }

        if(formID != null)
            formIDService.addFormID(userID, formID);

        if(StringUtils.isBlank(card.getTitle())){
            return JSONResult.errorMsg("卡片标题为空");
        }

        if(StringUtils.isBlank(card.getContent())){
            return JSONResult.errorMsg("卡片内容为空");
        }

        if(StringUtils.isBlank(labelContent)){
            labelContent="#General";
        }

        card.setUserId(userID);

        String[] labelContents=labelContent.split(" ");
        card.setLabelNum(labelContents.length);
        //存储标签
        Long cardID=cardService.saveCard(card); //获取存储的卡片id
        cardService.saveLabel(userID ,cardID, labelContents);

        return JSONResult.ok(cardService.queryCardByCardID(cardID));
    }
//
//    @ApiOperation(value = "记住/忘记卡片", notes = "记住/忘记卡片")
//    @ApiImplicitParams({@ApiImplicitParam(name = "userID", value = "userID", required = true, dataType = "Long", paramType = "query"),
//            @ApiImplicitParam(name = "sessionToken", value = "sessionToken", required = true, dataType = "String", paramType = "query"),
//            @ApiImplicitParam(name = "cardID", value = "cardID", required = true, dataType = "Long", paramType = "query"),
//            @ApiImplicitParam(name = "remember", value = "remember", required = true, dataType = "boolean", paramType = "query"),
//            @ApiImplicitParam(name = "formID", value = "formID", required = true, dataType = "String", paramType = "query")})
//    @ApiResponses({ @ApiResponse(code = 502, message = "Invalid Session Token"),
//            @ApiResponse(code = 502, message = "Parameter missing"),
//            @ApiResponse(code = 200, message = "ok") })
//    @PostMapping("/rememberCardOrNot")
//    public JSONResult rememberCard(Long userID, String sessionToken, Long cardID, boolean remember, String formID){
//        if(!sessionTokenIsValid(userID, sessionToken)){
//            return JSONResult.errorTokenMsg(INVALID_SESSION_TOKEN);
//        }
//
//        if(formID != null)
//            formIDService.addFormID(userID, formID);
//
//        if(cardID == null){
//            return JSONResult.errorMsg(PARAM_MISSING+"cardID");
//        }
//        FrCard card = cardService.queryCardByCardID(cardID);
//        card = cardService.nextTime(card, remember);
//        return JSONResult.ok(cardService.getCardLabels(card));
//    }

}
