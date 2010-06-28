/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.alfredwesterveld.scheduler;

import com.alfredwesterveld.webserver.ScheduleBroadcasterWebService;

/**
 *
 * @author alfred
 */
public class ScheduleProcessor {
    private final Scheduler<?> scheduler;
    
    public ScheduleProcessor(Scheduler<?> scheduler) {
        this.scheduler = scheduler;
    }

    public void process() {
        while (true) {
            try {
                Task<?> run = scheduler.run();
                Object job = run.getJob();
                ScheduleBroadcasterWebService.broadcast(job.toString());
            } catch (InterruptedException ex) {
                break;
            }
        }
    }
}
