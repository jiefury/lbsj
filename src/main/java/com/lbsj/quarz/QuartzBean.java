package com.lbsj.quarz;

import lombok.Data;

import java.util.Date;

@Data
public class QuartzBean {
    private String id;
    private String jobName;
    private String jobGroupName;
    private Integer status;
    private String state;
    private String cronExp;
    private String triggerName;
    private String triggerGroup;
    private Date startAt;

}
