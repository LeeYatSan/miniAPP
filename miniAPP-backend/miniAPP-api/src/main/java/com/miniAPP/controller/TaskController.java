package com.miniAPP.controller;

import com.miniAPP.mapper.FrUserLoginMapper;
import com.miniAPP.pojo.VO.TemplateDataVO;
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
//    @Scheduled(cron = "0/15 * * * * ?") //每10秒执行一次
//    @Scheduled(cron = "0 */1 * * * ?") //每1分钟执行一次
    @Scheduled(cron = "0 0 9,15,22 * * ?") //每天9、15、22点执行一次
    private void configureTasks() {

        List<Long> userList = cardService.queryUserNeededToBeNoticed();
        final String TEMPLATE_ID = "YOxMSObL6dkgajB2SpqxRuBTunjBoJwulie0_-LbYRg";
        Calendar cal=java.util.Calendar.getInstance();
        SimpleDateFormat format=new java.text.SimpleDateFormat("yyyy年MM月dd日");
        String date = format.format(cal.getTime());

        //获取AccessToken
        String url = "https://api.weixin.qq.com/cgi-bin/token";
        Map<String, String> param = new HashMap<>();
        param.put("grant_type", "client_credential");
        param.put("appid", APPID);
        param.put("secret", SECRET);
        WXAccessTokenModel model = JsonUtils.jsonToPojo(HttpClientUtil.doGet(url, param), WXAccessTokenModel.class);

        //获取AccessToken失败时直接结束
        if(model == null || model.getErrcode() != 0)
            return;

        //设置模版信息
        url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=" + model.getAccess_token();
        for(int i = 0; i < userList.size(); ++i){

            Long curr = userList.get(i);
            String formID = formIDService.getFormid(curr);
            if(formID == null)
                continue;

            //设置模板消息内容
            Map<String, TemplateDataVO> data = new HashMap<>();
            data.put("keyword1", new TemplateDataVO("您今天还有卡片需要记忆哟～"));
            data.put("keyword2", new TemplateDataVO(date));
            data.put("keyword3", new TemplateDataVO(String.format("今日还要记住 %d 张卡哦！", cardService.queryUnfamiliarCardNum(curr))));

            TemplateVO templateVO = new TemplateVO();
            templateVO.setPage("");
            templateVO.setTemplate_id(TEMPLATE_ID);
            templateVO.setTouser(frUserLoginMapper.selectByPrimaryKey(curr).getUserOpenid());
            templateVO.setForm_id(formID);
            templateVO.setData(data);

            String result = HttpClientUtil.doPostJson(url, JsonUtils.objectToJson(templateVO));
            WXSendTemplateMsgModel sendTemplateMsgModel = JsonUtils.jsonToPojo(result, WXSendTemplateMsgModel.class);

            //如果推送失败重新加入列表进行推送
            if(sendTemplateMsgModel == null || sendTemplateMsgModel.getErrcode() != 0)
                userList.add(curr);

            System.err.println("执行完毕：" + Calendar.getInstance().getTimeInMillis());
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
