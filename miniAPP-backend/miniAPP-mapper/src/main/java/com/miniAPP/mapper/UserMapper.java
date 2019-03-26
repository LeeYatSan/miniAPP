package com.miniAPP.mapper;

import com.miniAPP.pojo.User;
import com.miniAPP.utils.MyMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends MyMapper<User> {
}