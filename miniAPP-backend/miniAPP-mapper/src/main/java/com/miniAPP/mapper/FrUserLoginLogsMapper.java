package com.miniAPP.mapper;

import com.miniAPP.pojo.FrUserLogin;
import com.miniAPP.pojo.FrUserLoginLogs;
import com.miniAPP.utils.MyMapper;
import org.springframework.stereotype.Component;

@Component(value = "FrUserLoginLogsMapper")
public interface FrUserLoginLogsMapper extends MyMapper<FrUserLoginLogs> {
    interface FrUserLoginMapper extends MyMapper<FrUserLogin> {
    }
}