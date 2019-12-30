package com.gsmzzk.service;

import com.gsmzzk.common.ServerResponse;
import com.gsmzzk.pojo.Qualityinfo;

public interface IQualityService {


    public ServerResponse save(Qualityinfo qualityinfo);
    public ServerResponse findByPage(Integer pageNum,Integer pageSize);
    public ServerResponse findInfobyids(String ids);
}
