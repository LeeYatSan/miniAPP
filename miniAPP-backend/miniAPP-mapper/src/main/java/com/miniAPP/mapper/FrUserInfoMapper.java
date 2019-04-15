package com.miniAPP.mapper;

import com.miniAPP.pojo.FrUserInfo;
import com.miniAPP.utils.MyMapper;
import org.springframework.stereotype.Component;

@Component(value = "FrUserInfoMapper")
public interface FrUserInfoMapper extends MyMapper<FrUserInfo> {
}