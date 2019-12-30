package com.gsmzzk.service.impl;

import com.gsmzzk.common.ResponseCode;
import com.gsmzzk.common.ServerResponse;
import com.gsmzzk.mapper.UserMapper;
import com.gsmzzk.pojo.UserInfo;
import com.gsmzzk.service.IUserService;
import com.gsmzzk.utils.MD5Utils;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public ServerResponse login(String username, String password) {

        if(StringUtils.isBlank(username)){
            return ServerResponse.createServerResponseByFail(ResponseCode.USERNAME_EMPTY,"用户名不能为空");
        }
        if(StringUtils.isBlank(password)){
            return ServerResponse.createServerResponseByFail(ResponseCode.PASSWORD_EMPTY,"密码不能为空");
        }

        //判断用户名是否存在
        int total_username=userMapper.isexistsUsername(username);
        if(total_username==0){//用户名不存在
            return ServerResponse.createServerResponseByFail(ResponseCode.USERNAME_NOT_EXISTS,"用户名不存在");
        }
        //判断用户名和密码是否存在

        UserInfo userInfo=userMapper.login(username, MD5Utils.getMD5Code(password));
        if(userInfo==null){
            return ServerResponse.createServerResponseByFail(ResponseCode.PASSWORD_INCORRENT,"密码错误");
        }
        return ServerResponse.createServerResponseBySucess(userInfo);
    }
}
