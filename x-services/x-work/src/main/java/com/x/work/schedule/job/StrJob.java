package com.x.work.schedule.job;

import com.x.work.schedule.constants.ScheduleConstant;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author : xuemingqi
 * @since : 2023/11/29 11:05
 */
@Slf4j
@DisallowConcurrentExecution
public class StrJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) {
        String msg = context.getJobDetail().getJobDataMap().getString(ScheduleConstant.COMMON_ID_KEY);
        log.info("你好! {}", msg);
    }
}
