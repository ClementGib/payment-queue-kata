package com.cacib.pqk.application.scheduler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.Serializable;

@Configuration
@EnableScheduling
public class SchedulerImpl implements Scheduler, Serializable {
    Logger logger = LoggerFactory.getLogger(SchedulerImpl.class);

    @Override
    @Scheduled(fixedRate = 10000)
    public void processQueue() {
        logger.info("Scheduler start");
        logger.info("Scheduler end");
    }
}
