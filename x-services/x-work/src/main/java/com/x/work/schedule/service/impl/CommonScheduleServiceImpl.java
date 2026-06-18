package com.x.work.schedule.service.impl;

import com.x.work.schedule.constants.ScheduleConstant;
import com.x.work.schedule.service.CommonScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : xuemingqi
 * @since : 2023/11/29 10:37
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class CommonScheduleServiceImpl implements CommonScheduleService {

    private final Scheduler scheduler;

    @SneakyThrows
    @Override
    public void addSchedule(Class<? extends Job> jobClass, String id, String cron) {
        CronTrigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity(new TriggerKey(ScheduleConstant.COMMON_TRIGGER_KEY + id))
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .build();
        scheduler.scheduleJob(buildJobDetail(jobClass, id), trigger);
    }

    @SneakyThrows
    @Override
    public void deleteSchedule(String id) {
        scheduler.deleteJob(new JobKey(ScheduleConstant.COMMON_JOB_KEY + id));
    }

    private JobDetail buildJobDetail(Class<? extends Job> jobClass, String id) {
        return JobBuilder.newJob(jobClass)
                .withIdentity(new JobKey(ScheduleConstant.COMMON_JOB_KEY + id))
                .usingJobData(ScheduleConstant.COMMON_ID_KEY, id)
                .build();
    }
}
