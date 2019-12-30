package com.gsmzzk.controller;

import com.gsmzzk.common.ServerResponse;
import com.gsmzzk.pojo.Baseinfo;
import com.gsmzzk.pojo.Qualityinfo;
import com.gsmzzk.service.IBaseInfoService;
import com.gsmzzk.service.IQualityService;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/front")
public class FrontController {


    @Autowired
    IBaseInfoService baseInfoService;
    @Autowired
    IQualityService qualityService;
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public String showFront(){
        return "front";
    }


    @RequestMapping(value = "/info2",method = RequestMethod.GET)
    public String showFront2(){
        return "front2";
    }


    @RequestMapping(value = "/info",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse saveBaseinfo(Baseinfo baseinfo){

        System.out.println(baseinfo);

        return  baseInfoService.saveBaseInfo(baseinfo);
    }

    @RequestMapping(value = "/info2",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse save(Qualityinfo qualityinfo){

        return  qualityService.save(qualityinfo);
    }

}
