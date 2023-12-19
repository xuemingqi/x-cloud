package com.x.work.schedule.service.impl;

import cn.hutool.core.date.DateTime;
import com.x.work.schedule.constants.ScheduleConstant;
import com.x.work.schedule.job.HelloJob;
import com.x.work.schedule.service.HelloScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author : xuemingqi
 * @since : 2023/11/28 13:48
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class HelloScheduleServiceImpl implements HelloScheduleService {

    private final Scheduler scheduler;


    @SneakyThrows
    @Override
    public void addHelloJob(Long userId, LocalDateTime startTime) {
        SimpleTrigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity(new TriggerKey(ScheduleConstant.SAY_HELLO_TRIGGER_KEY + userId))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).repeatForever())
                .startAt(new DateTime(startTime))
                .build();
        scheduler.scheduleJob(buildJobDetail(userId), trigger);
    }

    @SneakyThrows
    @Override
    public void addHelloJob(Long userId, String cron) {
        CronTrigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity(new TriggerKey(ScheduleConstant.SAY_HELLO_TRIGGER_KEY + userId))
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .build();
        scheduler.scheduleJob(buildJobDetail(userId), trigger);
    }

    @SneakyThrows
    @Override
    public void deleteHelloJob(Long userId) {
        scheduler.deleteJob(new JobKey(ScheduleConstant.SAY_HELLO_JOB_KEY + userId));
    }

    private JobDetail buildJobDetail(Long userId) {
        return JobBuilder.newJob(HelloJob.class)
                .withIdentity(new JobKey(ScheduleConstant.SAY_HELLO_JOB_KEY + userId))
                .usingJobData(ScheduleConstant.USER_ID_KEY, userId)
                .withDescription("say hello to user")
                .build();
    }
}
