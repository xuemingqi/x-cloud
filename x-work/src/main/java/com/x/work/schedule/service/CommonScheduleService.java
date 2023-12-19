package com.x.work.schedule.service;

import org.quartz.Job;

/**
 * @author : xuemingqi
 * @since : 2023/11/29 10:35
 */
public interface CommonScheduleService {

    /**
     * 添加任务
     *
     * @param jobClass 任务
     * @param id       任务id
     * @param cron     表达式
     */
    void addSchedule(Class<? extends Job> jobClass, String id, String cron);

    /**
     * 删除任务
     *
     * @param id 任务id
     */
    void deleteSchedule(String id);
}
