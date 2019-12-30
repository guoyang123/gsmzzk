package com.gsmzzk.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gsmzzk.common.ResponseCode;
import com.gsmzzk.common.ServerResponse;
import com.gsmzzk.mapper.QualityinfoMapper;
import com.gsmzzk.pojo.Baseinfo;
import com.gsmzzk.pojo.Qualityinfo;
import com.gsmzzk.service.IQualityService;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class QualityServiceImpl implements IQualityService {
    @Autowired
    QualityinfoMapper qualityinfoMapper;
    @Override
    public ServerResponse save(Qualityinfo qualityinfo) {

        int count=qualityinfoMapper.insert(qualityinfo);
        if(count==0){
            return ServerResponse.createServerResponseByFail(ResponseCode.QUALITY_SAVE_FAIL,"质量指标添加失败");

        }
        return ServerResponse.createServerResponseBySucess("添加成功");
    }

    @Override
    public ServerResponse findByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Qualityinfo> baseinfoList=qualityinfoMapper.selectAll();
        PageInfo pageInfo=new PageInfo(baseinfoList);

        return ServerResponse.createServerResponseBySucess(pageInfo);
    }

    @Override
    public ServerResponse findInfobyids(String ids) {
        if(StringUtils.isBlank(ids)){
            return ServerResponse.createServerResponseByFail(ResponseCode.IDS_EMPTY,"请选中下载的记录");
        }
        String[] idsArray=ids.split(",");
        List<String> idList= Arrays.asList(idsArray);
        List<Qualityinfo> baseinfoList=qualityinfoMapper.findByIds(idList);

        System.out.println(baseinfoList);
        return ServerResponse.createServerResponseBySucess(baseinfoList);

    }
}
