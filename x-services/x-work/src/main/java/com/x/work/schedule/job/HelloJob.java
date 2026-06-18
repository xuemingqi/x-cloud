package com.x.work.schedule.job;

import com.x.work.schedule.constants.ScheduleConstant;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author : xuemingqi
 * @since : 2023/11/28 13:54
 */
@Slf4j
@DisallowConcurrentExecution
public class HelloJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) {
        Long userId = context.getJobDetail().getJobDataMap().getLong(ScheduleConstant.USER_ID_KEY);
        log.info("hello! {}", userId);
    }
}
