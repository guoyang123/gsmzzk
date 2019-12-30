package com.gsmzzk.mapper;

import com.gsmzzk.pojo.Baseinfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BaseInfoMapper {

    public Integer save(@Param("baseinfo")Baseinfo baseinfo);


    public List<Baseinfo> findByPage(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);

    public List<Baseinfo> findByIds(@Param("ids")List<String> idList);
}
