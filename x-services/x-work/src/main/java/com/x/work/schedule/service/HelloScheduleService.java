package com.x.work.schedule.service;

import java.time.LocalDateTime;

/**
 * @author : xuemingqi
 * @since : 2023/11/28 13:44
 */
public interface HelloScheduleService {

    /**
     * say hello to specific user
     *
     * @param userId    user's id
     * @param startTime start time
     */
    void addHelloJob(Long userId, LocalDateTime startTime);

    /**
     * say hello to specific user
     *
     * @param userId user's id
     * @param cron   cron expressions
     */
    void addHelloJob(Long userId, String cron);

    /**
     * delete hello job
     *
     * @param userId user's id
     */
    void deleteHelloJob(Long userId);
}
