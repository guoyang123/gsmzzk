package com.gsmzzk.service;

import com.gsmzzk.common.ServerResponse;
import com.gsmzzk.pojo.Baseinfo;

public interface IBaseInfoService {


    public ServerResponse saveBaseInfo(Baseinfo baseinfo);
    public ServerResponse getBaseInfos(Integer pageNum,Integer pageSize);

    public ServerResponse findInfobyids(String ids);
}
