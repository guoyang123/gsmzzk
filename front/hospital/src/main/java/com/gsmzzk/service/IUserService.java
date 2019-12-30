package com.gsmzzk.service;

import com.gsmzzk.common.ServerResponse;

public interface IUserService {

    public ServerResponse login(String username,String password);

}
