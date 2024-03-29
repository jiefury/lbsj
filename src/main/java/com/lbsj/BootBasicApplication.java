package com.lbsj;

import cn.hutool.cron.CronUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.lbsj.job.IJobService;
import com.lbsj.utils.SpringUtils;
import com.lbsj.work.impl.WorkServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;


@EnableAsync
@SpringBootApplication
public class BootBasicApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootBasicApplication.class, args);
        try {
            File readme = ResourceUtils.getFile("");
            System.out.println("----------" + readme.getAbsolutePath());
            System.out.println("----------" + System.getProperty("user.dir"));
            //静态配置定时任务，执行setting中的配置
            CronUtil.setMatchSecond(true);
            CronUtil.start();
            IJobService jobService = SpringUtils.getBean(IJobService.class);
            jobService.start();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
