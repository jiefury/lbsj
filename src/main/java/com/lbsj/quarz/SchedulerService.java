package com.lbsj.quarz;

import org.jetbrains.annotations.NotNull;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;


@Service
public class SchedulerService {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    private Scheduler scheduler;

    public Scheduler getScheduler() {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        return scheduler;
    }

    public void addJob(QuartzBean job) throws Exception {
        JobKey jobKey = getJobKey(job);
        TriggerKey triggerKey = getTriggerKey(job);
        boolean exists = scheduler.checkExists(jobKey);
        Trigger trigger = scheduler.getTrigger(triggerKey);
        if (exists && trigger != null) {
            return;
        }
        if (exists) {
            if (trigger == null) {
                trigger = getTrigger(job.getCronExp(), jobKey, triggerKey);
                scheduler.scheduleJob(trigger);
            }
        } else {
            JobDetail jobDetail = newJob(TestJob.class).withIdentity(jobKey).build();
            if (null != trigger) {
                trigger = getTrigger(job.getCronExp(), jobKey, triggerKey);
                scheduler.rescheduleJob(triggerKey, trigger);
            } else {
                trigger = getTrigger(job.getCronExp(), jobKey, triggerKey);
                scheduler.scheduleJob(jobDetail, trigger);
            }
        }

    }

    @NotNull
    private static TriggerKey getTriggerKey(QuartzBean job) {
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getTriggerName(), job.getTriggerGroup());
        return triggerKey;
    }

    @NotNull
    private static JobKey getJobKey(QuartzBean job) {
        JobKey jobKey = new JobKey(job.getJobName(), job.getJobGroupName());
        return jobKey;
    }

    private static Trigger getTrigger(QuartzBean job) {
        JobKey jobKey = getJobKey(job);
        TriggerKey triggerKey = getTriggerKey(job);
        TriggerBuilder<Trigger> builder = newTrigger();
        SimpleScheduleBuilder simpleSchedule = SimpleScheduleBuilder.simpleSchedule();
        builder.withSchedule(simpleSchedule);
        builder.forJob(jobKey);
        builder.withIdentity(triggerKey);//未执行的策略默认未0，会立即执行
        if (job.getCronExp() != null) {
            builder.withSchedule(CronScheduleBuilder.cronSchedule(job.getCronExp()));
        }
        if (job.getStartAt() != null) {
            builder.startAt(job.getStartAt());
        } else {
            builder.startNow();
        }
        return builder.build();
    }


    private static Trigger getTrigger(String cronExp, JobKey jobKey, TriggerKey triggerKey) {
        return newTrigger().withIdentity(triggerKey).startNow().forJob(jobKey).withSchedule(CronScheduleBuilder.cronSchedule(cronExp)).build();
    }

    public List<QuartzBean> getJobs() throws Exception {
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
        List<QuartzBean> objects = new ArrayList<>();
        for (JobKey jobKey : jobKeys) {
            QuartzBean quartzBean = new QuartzBean();
            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
            quartzBean.setJobName(jobKey.getName());
            quartzBean.setJobGroupName(jobKey.getGroup());
            for (Trigger trigger : triggers) {
                Trigger.TriggerState state = scheduler.getTriggerState(trigger.getKey());
                quartzBean.setState(state.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String expression = cronTrigger.getCronExpression();
                    quartzBean.setCronExp(expression);
                }
                TriggerKey key = trigger.getKey();
                String name = key.getName();
                String group = key.getGroup();
                quartzBean.setTriggerName(name);
                quartzBean.setTriggerGroup(group);
            }
            objects.add(quartzBean);
        }
        return objects;
    }

    //停止 pauseJob
    public void pauseJob(QuartzBean job) throws SchedulerException {
        JobKey jobKey = getJobKey(job);
        scheduler.pauseJob(jobKey);
        TriggerKey triggerKey = getTriggerKey(job);
        Trigger trigger = getTrigger(job);

    }

    //恢复 resumeJob
    public void resumeJob(QuartzBean job) throws SchedulerException {
        JobKey jobKey = getJobKey(job);
        TriggerKey triggerKey = getTriggerKey(job);
        Trigger trigger = getTrigger(job);
//        scheduler.rescheduleJob(triggerKey, trigger);
        scheduler.resumeJob(jobKey);
    }
    //删除 delete
    //立即执行 start
}
