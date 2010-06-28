package com.alfredwesterveld;

import com.alfredwesterveld.scheduler.ScheduleProcessor;
import com.alfredwesterveld.scheduler.Scheduler;
import com.alfredwesterveld.webserver.WebServerConfigurator;
import org.atmosphere.grizzly.AtmosphereSpadeServer;

/**
 * Hello world!
 *
 */
public class App {
    public static final String HOST = "http://localhost:8888/";

    public static final Scheduler<String> SCHEDULER = new Scheduler<String>();

    public static Scheduler<String> getScheduler() {
        return SCHEDULER;
    }
    
    public static void main(String[] args) throws Exception {
        String host = HOST;
        if (args.length > 1) {
            host = args[0];
        }
        
        AtmosphereSpadeServer setup = 
            WebServerConfigurator.setup(host);
        setup.start();
        new ScheduleProcessor(SCHEDULER).process();
    }
}
