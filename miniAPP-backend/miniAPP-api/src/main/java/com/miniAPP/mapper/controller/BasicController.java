package com.miniAPP.mapper.controller;

import com.miniAPP.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicController {

    @Autowired
    protected RedisOperator redis;

    public static final String USER_REDIS_SESSION = "user-redis-session";
}
