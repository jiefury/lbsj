package com.lbsj.job;

public interface IJobService {
    /**
     * 开启任务
     *
     * @return
     */
    String start();

    /**
     * 关闭任务
     *
     * @param jobId
     * @return
     */
    boolean stop(String jobId);
}
