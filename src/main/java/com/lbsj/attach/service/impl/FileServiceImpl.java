package com.lbsj.attach.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lbsj.attach.mapper.FileMapper;
import com.lbsj.attach.model.BaseFileEntity;
import com.lbsj.attach.service.FileService;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, BaseFileEntity> implements FileService {


}
