package com.lbsj.quarz;

import org.quartz.ee.servlet.QuartzInitializerListener;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Properties;

//@Configuration
public class QuartzConfig implements SchedulerFactoryBeanCustomizer {

    @Bean
    public Properties properties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        // 对quartz.properties文件进行读取
        propertiesFactoryBean.setLocation(new ClassPathResource("config/quartz.properties"));
        // 在quartz.properties中的属性被读取并注入后再初始化对象
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource) throws IOException {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        schedulerFactoryBean.setDataSource(dataSource);
        schedulerFactoryBean.setQuartzProperties(properties());
        schedulerFactoryBean.setAutoStartup(true);
        schedulerFactoryBean.setStartupDelay(5);
        return schedulerFactoryBean;
    }

    /*
     * quartz初始化监听器
     */
    @Bean
    public QuartzInitializerListener executorListener() {
        return new QuartzInitializerListener();
    }

//    /*
//     * 通过SchedulerFactoryBean获取Scheduler的实例
//     */
//    @Bean
//    public Scheduler scheduler(DataSource dataSource) throws IOException {
//        return schedulerFactoryBean(dataSource).getScheduler();
//    }
//
//    /**
//     * 使用阿里的druid作为数据库连接池
//     */
//    @Override
    public void customize(@NotNull SchedulerFactoryBean schedulerFactoryBean) {
        schedulerFactoryBean.setStartupDelay(2);
        schedulerFactoryBean.setAutoStartup(true);
        schedulerFactoryBean.setOverwriteExistingJobs(true);
    }
}
