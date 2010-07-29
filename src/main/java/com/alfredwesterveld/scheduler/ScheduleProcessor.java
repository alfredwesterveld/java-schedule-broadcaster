/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.alfredwesterveld.scheduler;

import com.alfredwesterveld.redis_netty.Redis;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alfred
 */
public class ScheduleProcessor {
    private final Scheduler<?> scheduler;

    private final Redis redis;

    public ScheduleProcessor(Scheduler<?> scheduler, Redis redis) {
        this.scheduler = scheduler;
        this.redis = redis;
    }

    public void process() {
        while (true) {
            try {
                Task<?> run = scheduler.get();
                Object job = run.getTask();
                Logger.getLogger(ScheduleProcessor.class.getName())
                    .log(Level.CONFIG, job.toString());
                redis.rpush("scheduler", job.toString());
            } catch (InterruptedException ex) {
                break;
            }
        }
    }
}
