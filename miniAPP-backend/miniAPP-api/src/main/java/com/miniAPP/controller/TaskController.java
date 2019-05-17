package com.miniAPP.controller;

import com.miniAPP.mapper.FrUserLoginMapper;
import com.miniAPP.pojo.VO.TemplateVO;
import com.miniAPP.pojo.WXModel.WXAccessTokenModel;
import com.miniAPP.pojo.WXModel.WXSendTemplateMsgModel;
import com.miniAPP.service.CardService;
import com.miniAPP.service.FormIDService;
import com.miniAPP.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@EnableScheduling
public class TaskController extends BasicController {

    @Autowired
    CardService cardService;

    @Autowired
    FrUserLoginMapper frUserLoginMapper;

    @Autowired
    FormIDService formIDService;

    /**
     * 定时推送任务
     */
    //@Scheduled(cron = "0/5 * * * * ?") //每5秒执行一次
    //@Scheduled(cron = "0 */1 * * * ?") //每1分钟执行一次
    //@Scheduled(cron = "0 0 15 * * ?") //每天15点执行一次
    private void configureTasks() {
        System.err.println("每日15点推送: " + Calendar.getInstance().getTimeInMillis());

        List<Long> userList = cardService.queryUserNeededToBeNoticed();
        //获取AccessToken
        String url = "https://api.weixin.qq.com/cgi-bin/token";
        Map<String, String> param = new HashMap<>();
        param.put("appid", APPID);
        param.put("secret", SECRET);
        String wxResult = HttpClientUtil.doGet(url, param);
        WXAccessTokenModel model = JsonUtils.jsonToPojo(wxResult, WXAccessTokenModel.class);
        String accessToken = model.getAccess_token();
        final String TEMPLATE_ID = "YOxMSObL6dkgajB2SpqxRuBTunjBoJwulie0_-LbYRg";

        Calendar cal=java.util.Calendar.getInstance();
        SimpleDateFormat format=new java.text.SimpleDateFormat("yyyy年MM月dd日");
        String date = format.format(cal.getTime());

        //设置模版信息
        url = "https://api.weixin.qq.com/cgibin/message/wxopen/template/send";
        for(int i = 0; i < userList.size(); ++i){

            Long curr = userList.get(i);
            String formID = formIDService.getFormid(curr);
            if(formID == null)
                continue;

            param = new HashMap<>();
            TemplateVO templateVO = new TemplateVO(date, cardService.queryUnfamiliarCardNum(curr));

            param.put("access_token",accessToken);
            param.put("touser", frUserLoginMapper.selectByPrimaryKey(curr).getUserOpenid());
            param.put("template_id",TEMPLATE_ID); //模版信息ID
            param.put("form_id", formID);
            param.put("data", JsonUtils.objectToJson(templateVO));//设置模板消息内容
            //param.put("page", page);//跳转微信小程序页面路径（非必须）

            WXSendTemplateMsgModel sendTemplateMsgModel = JsonUtils.jsonToPojo(HttpClientUtil.doPost(url, param), WXSendTemplateMsgModel.class);

            //如果推送失败重新加入列表进行推送
            if(sendTemplateMsgModel.getErrcode() != 0)
                userList.add(curr);
        }
    }

//    /**
//     * 自动检查更新
//     */
//    @Scheduled(cron = "0 */5 * * * ?") //每5分钟执行一次
//    private JSONResult autoUpdate() {
//        System.err.println("自动检查更新: " + Calendar.getInstance().getTimeInMillis());
//
//
//    }



}
