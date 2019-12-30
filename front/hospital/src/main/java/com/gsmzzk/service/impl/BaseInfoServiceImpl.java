package com.gsmzzk.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gsmzzk.common.ResponseCode;
import com.gsmzzk.common.ServerResponse;
import com.gsmzzk.mapper.BaseInfoMapper;
import com.gsmzzk.pojo.Baseinfo;
import com.gsmzzk.service.IBaseInfoService;

import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class BaseInfoServiceImpl implements IBaseInfoService {
    @Autowired
    BaseInfoMapper baseInfoMapper;
    @Override
    public ServerResponse saveBaseInfo(Baseinfo baseinfo) {

        Integer result=baseInfoMapper.save(baseinfo);


        if(result<=0){
           return  ServerResponse.createServerResponseByFail(ResponseCode.ERROR,"保存失败");
        }
        return ServerResponse.createServerResponseBySucess("添加成功");
    }

    @Override
    public ServerResponse getBaseInfos(Integer pageNum, Integer pageSize) {


        PageHelper.startPage(pageNum,pageSize);
        List<Baseinfo> baseinfoList=baseInfoMapper.findByPage(pageNum, pageSize);
        PageInfo pageInfo=new PageInfo(baseinfoList);

        return ServerResponse.createServerResponseBySucess(pageInfo);
    }

    @Override
    public ServerResponse findInfobyids(String ids) {

        if(StringUtils.isBlank(ids)){
            return ServerResponse.createServerResponseByFail(ResponseCode.IDS_EMPTY,"请选中下载的记录");
        }
        String[] idsArray=ids.split(",");
        List<String> idList=Arrays.asList(idsArray);
        List<Baseinfo> baseinfoList=baseInfoMapper.findByIds(idList);

        System.out.println(baseinfoList);
        return ServerResponse.createServerResponseBySucess(baseinfoList);
    }


}
