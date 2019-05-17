package com.miniAPP.controller;

import com.miniAPP.pojo.FrUserLogin;
import com.miniAPP.pojo.WXModel.WXSessionModel;
import com.miniAPP.service.FormIDService;
import com.miniAPP.service.UserService;
import com.miniAPP.utils.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.Map;

@RestController
@Api(value = "登录态管理API", tags = {"Login/Logout/Register Controller"})
public class LoginController extends BasicController{


    @Autowired
    private UserService userService;

    @Autowired
    private FormIDService formIDService;

    @ApiOperation(value = "重新登录/注册", notes = "重新登录/注册API")
    @ApiImplicitParam(name = "code", value = "wx-code", required = true, dataType = "String", paramType = "query")
    @ApiResponses({ @ApiResponse(code = 500, message = "Invalid wx.request"), @ApiResponse(code = 200, message = "ok") })
    @PostMapping("/onRegister")
    public JSONResult doRegister(String code) throws Exception{

        //1. 判断是否成功接收code
        if(code == null)
            return JSONResult.errorMsg("FAIL TO GET USER INFO");

        //2. 解析用户信息
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        Map<String, String>param = new HashMap<>();
        param.put("appid", APPID);
        param.put("secret", SECRET);
        param.put("js_code", code);
        param.put("grant_type", "authorization_code");

        String wxResult = HttpClientUtil.doGet(url, param);
        WXSessionModel model = JsonUtils.jsonToPojo(wxResult, WXSessionModel.class);

        //3. 检查用户是否存在
        boolean userIsExist = userService.queryOpenidIsExist(model.getOpenid());

        //4. 如果用户不存在，则自动注册并保存用户信息以及登录信息
        FrUserLogin userLogin = new FrUserLogin();
        userLogin.setUserOpenid(model.getOpenid());
        userLogin.setUserState(true);
        Long userID;
        if(!userIsExist){
            userID = userService.saveUser(userLogin);
        }
        else{
            userID = userService.queryUserID(model.getOpenid());
        }
        //用户登录信息记录
        userService.userLoginRec(userID);

        //5. 建立session token
        String sessionToken = MD5Utils.getMD5Str(model.getSession_key());
        redis.set(USER_REDIS_SESSION+":"+userID, sessionToken);

        //6.返回用户信息JSON
        return JSONResult.ok(userService.queryUserInfo(userID, sessionToken));
    }

    @ApiOperation(value = "登录", notes = "登录API")
    @ApiImplicitParams({@ApiImplicitParam(name = "userID", value = "userID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sessionToken", value = "sessionToken", required = true, dataType = "String", paramType = "query")})
    @ApiResponses({ @ApiResponse(code = 502, message = "Invalid Session Token"), @ApiResponse(code = 200, message = "ok") })
    @PostMapping("/onLogin")
    public JSONResult doLogin(Long userID, String sessionToken) throws Exception{

        //session token 有效
        if(sessionTokenIsValid(userID, sessionToken)){
            //用户登录信息记录
            userService.userLoginRec(userID);

            //返回用户信息JSON
            return JSONResult.ok(userService.queryUserInfo(userID, sessionToken));
        }
        // session token 无效
        else{
            return JSONResult.errorTokenMsg(INVALID_SESSION_TOKEN);
        }
    }


    @ApiOperation(value = "注销", notes = "注销API")
    @ApiImplicitParams({@ApiImplicitParam(name = "userID", value = "userID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sessionToken", value = "sessionToken", required = true, dataType = "String", paramType = "query")})
    @ApiResponses({ @ApiResponse(code = 502, message = "Invalid Session Token"), @ApiResponse(code = 200, message = "ok") })
    @PostMapping("/onLogout")
    public JSONResult doLogout(Long userID, String sessionToken) throws Exception{

        //session token 有效
        if(sessionTokenIsValid(userID, sessionToken)){
            userService.userLogout(userID);
            redis.del(USER_REDIS_SESSION + ":" + userID);
            return JSONResult.ok();
        }
        // session token 无效
        else{
            return JSONResult.errorTokenMsg(INVALID_SESSION_TOKEN);
        }
    }


    // 需要传formID
//    @ApiOperation(value = "重新登录/注册", notes = "重新登录/注册API")
//    @ApiImplicitParams({@ApiImplicitParam(name = "code", value = "wx-code", required = true, dataType = "String", paramType = "query"),
//            @ApiImplicitParam(name = "formID", value = "formID", required = true, dataType = "String", paramType = "query")})
//    @ApiResponses({ @ApiResponse(code = 500, message = "Invalid wx.request"), @ApiResponse(code = 200, message = "ok") })
//    @PostMapping("/onRegister")
//    public JSONResult doRegister(String code, String formID) throws Exception{
//
//        //1. 判断是否成功接收code
//        if(code == null)
//            return JSONResult.errorMsg("FAIL TO GET USER INFO");
//
//        //2. 解析用户信息
//        String url = "https://api.weixin.qq.com/sns/jscode2session";
//        Map<String, String>param = new HashMap<>();
//        param.put("appid", APPID);
//        param.put("secret", SECRET);
//        param.put("js_code", code);
//        param.put("grant_type", "authorization_code");
//
//        String wxResult = HttpClientUtil.doGet(url, param);
//        WXSessionModel model = JsonUtils.jsonToPojo(wxResult, WXSessionModel.class);
//
//        //3. 检查用户是否存在
//        boolean userIsExist = userService.queryOpenidIsExist(model.getOpenid());
//
//        //4. 如果用户不存在，则自动注册并保存用户信息以及登录信息
//        FrUserLogin userLogin = new FrUserLogin();
//        userLogin.setUserOpenid(model.getOpenid());
//        userLogin.setUserState(true);
//        Long userID;
//        if(!userIsExist){
//            userID = userService.saveUser(userLogin);
//        }
//        else{
//            userID = userService.queryUserID(model.getOpenid());
//        }
//        //用户登录信息记录
//        userService.userLoginRec(userID);
//        if(userID != null)
//            formIDService.addFormID(userID, formID);
//
//        //5. 建立session token
//        String sessionToken = MD5Utils.getMD5Str(model.getSession_key());
//        redis.set(USER_REDIS_SESSION+":"+userID, sessionToken);
//
//        //6.返回用户信息JSON
//        return JSONResult.ok(userService.queryUserInfo(userID, sessionToken));
//    }
//
//    @ApiOperation(value = "登录", notes = "登录API")
//    @ApiImplicitParams({@ApiImplicitParam(name = "userID", value = "userID", required = true, dataType = "String", paramType = "query"),
//            @ApiImplicitParam(name = "sessionToken", value = "sessionToken", required = true, dataType = "String", paramType = "query"),
//            @ApiImplicitParam(name = "formID", value = "formID", required = true, dataType = "String", paramType = "query")})
//    @ApiResponses({ @ApiResponse(code = 502, message = "Invalid Session Token"), @ApiResponse(code = 200, message = "ok") })
//    @PostMapping("/onLogin")
//    public JSONResult doLogin(Long userID, String sessionToken, String formID) throws Exception{
//
//        //session token 有效
//        if(sessionTokenIsValid(userID, sessionToken)){
//            //用户登录信息记录
//            userService.userLoginRec(userID);
//            if(userID != null)
//                formIDService.addFormID(userID, formID);
//
//            //返回用户信息JSON
//            return JSONResult.ok(userService.queryUserInfo(userID, sessionToken));
//        }
//        // session token 无效
//        else{
//            return JSONResult.errorTokenMsg(INVALID_SESSION_TOKEN);
//        }
//    }
//
//
//    @ApiOperation(value = "注销", notes = "注销API")
//    @ApiImplicitParams({@ApiImplicitParam(name = "userID", value = "userID", required = true, dataType = "String", paramType = "query"),
//            @ApiImplicitParam(name = "sessionToken", value = "sessionToken", required = true, dataType = "String", paramType = "query"),
//            @ApiImplicitParam(name = "formID", value = "formID", required = true, dataType = "String", paramType = "query")})
//    @ApiResponses({ @ApiResponse(code = 502, message = "Invalid Session Token"), @ApiResponse(code = 200, message = "ok") })
//    @PostMapping("/onLogout")
//    public JSONResult doLogout(Long userID, String sessionToken, String formID) throws Exception{
//
//        //session token 有效
//        if(sessionTokenIsValid(userID, sessionToken)){
//
//            if(userID != null)
//                formIDService.addFormID(userID, formID);
//
//            userService.userLogout(userID);
//            redis.del(USER_REDIS_SESSION + ":" + userID);
//            return JSONResult.ok();
//        }
//        // session token 无效
//        else{
//            return JSONResult.errorTokenMsg(INVALID_SESSION_TOKEN);
//        }
//    }


}
