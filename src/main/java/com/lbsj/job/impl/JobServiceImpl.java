package com.lbsj.job.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.cron.CronUtil;
import com.lbsj.config.AsyncConfiguration;
import com.lbsj.job.IJobService;
import com.lbsj.work.IWorkService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Log4j2
public class JobServiceImpl implements IJobService {

    @Autowired
    private IWorkService workService;

    @Override
    public String start() {
        log.info("开启定时任务");
        return work();
    }

    private String work() {
        String jobId = UUID.randomUUID().toString();
        String schedulingPattern = "0/5 * * * * ?";
        //新增定时任务
        CronUtil.schedule(jobId, schedulingPattern, () -> {
            log.info("【{}】执行定时任务【{}】", DateUtil.now(), jobId);
            workService.work();
        });
        return jobId;
    }

    @Override
    public boolean stop(String jobId) {
        log.info("关闭定时任务【{}】", jobId);
        return CronUtil.remove(jobId);
    }


}
