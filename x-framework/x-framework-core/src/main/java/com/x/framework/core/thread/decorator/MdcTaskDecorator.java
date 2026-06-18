package com.x.framework.core.thread.decorator;

import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

import java.util.Map;

/**
 * @author : xuemingqi
 * @since : 2024-10-09 15:41
 */
public class MdcTaskDecorator implements TaskDecorator {

    @Override
        public Runnable decorate(Runnable runnable) {
        Map<String, String> contextMap = MDC.getCopyOfContextMap();
        return () -> {
            try {
                if (contextMap != null) {
                    MDC.setContextMap(contextMap);
                }
                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }
}
