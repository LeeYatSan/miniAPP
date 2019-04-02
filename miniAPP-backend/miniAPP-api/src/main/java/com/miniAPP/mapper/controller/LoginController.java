package com.miniAPP.mapper.controller;

import com.miniAPP.pojo.User;
import com.miniAPP.service.UserService;
import com.miniAPP.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.Map;

@RestController
@Api(value = "Login/Logout - 登录/注销API")
public class LoginController extends BasicController{


    @Autowired
    private UserService userService;

    @ApiOperation(value = "登录", notes = "登录API")
    @ApiImplicitParam(name = "code", value = "wx-code", required = true, dataType = "String", paramType = "query")
    @PostMapping("/onLogin")
    public JSONResult doLogin(String code) throws Exception{

        //1. 判断是否成功接收code
        if(code == null)
            return JSONResult.errorMsg("FAIL TO GET USER INFO");

        //2. 解析用户信息
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        Map<String, String>param = new HashMap<>();
        param.put("appid", "wx988452b3ef2cc412");
        param.put("secret", "07e78d89c6d877176daed75944b4490d");
        param.put("js_code", code);
        param.put("grant_type", "authorization_code");

        String wxResult = HttpClientUtil.doGet(url, param);
        WXSessionModel model = JsonUtils.jsonToPojo(wxResult, WXSessionModel.class);

        //3. 检查用户是否存在
        boolean userOpenIDISExist = userService.queryOpenidIsExist(model.getOpenid());

        //4. 保存用户信息
        if(!userOpenIDISExist){
            User user = new User();
            user.setOpenid(model.getOpenid());
            userService.saveUser(user);
        }

        //5. 将session存入redis
        if(redis.get(USER_REDIS_SESSION+":"+model.getOpenid()) == null)
            redis.set(USER_REDIS_SESSION+":"+model.getOpenid(), model.getSession_key(), 1000*60*60*24);
        return JSONResult.ok(code);
    }



    @ApiOperation(value = "注销", notes = "注销API")
    @ApiImplicitParam(name = "openid", value = "openid", required = true, dataType = "String", paramType = "query")
    @PostMapping("/onLogout")
    public JSONResult doLogout(String openid) throws Exception{
        redis.del(USER_REDIS_SESSION + ":" + openid);
        return JSONResult.ok();
    }
}
