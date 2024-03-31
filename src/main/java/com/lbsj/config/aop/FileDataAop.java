package com.lbsj.config.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Log4j2
@Aspect
@Component
public class FileDataAop {

    @Pointcut("@annotation(com.lbsj.common.annotation.InsertFileData)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //上传时间
        //上传人
        //原始文件名称
        //重名名
        //路径
        Object[] args = joinPoint.getArgs();
        MultipartFile file = (MultipartFile) args[0];

        joinPoint.proceed(args);
    }
}
