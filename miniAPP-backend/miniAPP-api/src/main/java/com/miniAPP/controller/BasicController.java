package com.miniAPP.controller;

import com.miniAPP.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicController {

    @Autowired
    protected RedisOperator redis;

    public static final String APPID = "wx988452b3ef2cc412";

    public static final String SECRET = "07e78d89c6d877176daed75944b4490d";

    public static final String USER_REDIS_SESSION = "user-redis-session";

    public static final String INVALID_SESSION_TOKEN = "Invalid session token";

    public static final String PARAM_MISSING = "Param missing";


    /**
     * 检查用户session合法性
     */
    protected boolean sessionTokenIsValid(Long userID, String sessionToken){
        return sessionToken.equals(redis.get(USER_REDIS_SESSION+":"+userID));
    }
}
