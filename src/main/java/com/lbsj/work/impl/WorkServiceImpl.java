package com.lbsj.work.impl;

import cn.hutool.core.date.DateUtil;
import com.lbsj.work.IWorkService;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class WorkServiceImpl implements IWorkService {
    
    @Async
    @Override
    public String work() {
        log.info("执行任务:{}", DateUtil.now());
        int i = 1 / 0;
        return "workwork";
    }
}
