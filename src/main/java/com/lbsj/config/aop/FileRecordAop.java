package com.lbsj.config.aop;

import com.lbsj.attach.model.BaseFileEntity;
import com.lbsj.attach.service.FileService;
import com.lbsj.common.model.FileVO;
import com.lbsj.common.model.RequestResult;
import com.lbsj.config.SysParamsConfig;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Log4j2
@Aspect
@Component
public class FileRecordAop {

    @Autowired
    private FileService fileService;

    @Pointcut("@annotation(com.lbsj.common.annotation.FileRecord)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Object proceed = joinPoint.proceed(args);
        RequestResult<FileVO> res = (RequestResult<FileVO>) proceed;
        FileVO data = res.getData();
        BaseFileEntity entity = new BaseFileEntity();
        entity.setFilename(data.getFileName());
        entity.setFilePath(SysParamsConfig.TEMP_DIR_PATH + data.getFileUrl());
        entity.setSuffix(data.getSuffix());
        entity.setReFilename(data.getRefileName());
        fileService.save(entity);
        return proceed;
    }
}
