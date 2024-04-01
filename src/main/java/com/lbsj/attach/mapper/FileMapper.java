package com.lbsj.attach.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lbsj.attach.model.BaseFileEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper extends BaseMapper<BaseFileEntity> {
}
