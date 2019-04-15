package com.miniAPP.mapper;

import com.miniAPP.pojo.FrUserLogin;
import com.miniAPP.utils.MyMapper;
import org.springframework.stereotype.Component;

@Component(value = "FrUserLoginMapper")
public interface FrUserLoginMapper extends MyMapper<FrUserLogin> {
}