package com.x.work.service.impl;

import com.x.work.service.SseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : xuemingqi
 * @since : 2025/02/14 13:35
 */
@Slf4j
@Service
public class SseServiceImpl implements SseService {

    private final ConcurrentHashMap<String, SseEmitter> emitters = new ConcurrentHashMap<>();


    @Override
    public void put(String id, SseEmitter emitter) {
        emitters.put(id, emitter);
        emitter.onCompletion(() -> emitters.remove(id));
        emitter.onTimeout(() -> emitters.remove(id));
        emitter.onError((e) -> emitters.remove(id));
    }

    @Override
    public SseEmitter get(String id) {
        return emitters.get(id);
    }

    @Override
    public void remove(String id) {
        emitters.remove(id);
    }

    @Override
    public void notify(String id, String msg) {
        SseEmitter emitter = emitters.get(id);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().data(msg));
            } catch (Exception e) {
                log.error("notify error:{}", ExceptionUtils.getStackTrace(e));
            }
        }
    }

    @Override
    public void batchNotify(Set<String> ids, String msg) {
        ids.forEach(id -> {
            SseEmitter emitter = emitters.get(id);
            if (emitter != null) {
                try {
                    emitter.send(SseEmitter.event().data(msg));
                } catch (Exception e) {
                    log.error("id:{},batchNotify error:{}", id, ExceptionUtils.getStackTrace(e));
                }
            }
        });
    }

    @Override
    public void notifyAll(String msg) {
        emitters.forEach((k, v) -> {
            try {
                v.send(SseEmitter.event().data(msg));
            } catch (Exception e) {
                log.error("notifyAll error:{}", ExceptionUtils.getStackTrace(e));
            }
        });
    }
}
