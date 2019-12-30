package com.gsmzzk.mapper;

import com.gsmzzk.pojo.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    public Integer isexistsUsername(@Param("username")String username);
    public UserInfo login(@Param("username")String username,@Param("password")String password);
}
