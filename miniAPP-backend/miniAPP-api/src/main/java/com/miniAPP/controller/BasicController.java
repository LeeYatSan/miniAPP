package com.miniAPP.controller;

import com.miniAPP.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicController {

    @Autowired
    protected RedisOperator redis;

    public static final String USER_REDIS_SESSION = "user-redis-session";

    public static final String INVALID_SESSION_TOKEN = "Invalid session token";

    /**
     * 检查用户session合法性
     */
    protected boolean sessionTokenIsValid(Long userID, String sessionToken){
        return sessionToken.equals(redis.get(USER_REDIS_SESSION+":"+userID));
    }
}
