package com.alfredwesterveld;

import com.alfredwesterveld.redis_netty.Redis;
import com.alfredwesterveld.scheduler.ScheduleProcessor;
import com.alfredwesterveld.scheduler.Scheduler;
import com.alfredwesterveld.webserver.WebServerConfigurator;
import java.net.ConnectException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.atmosphere.grizzly.AtmosphereSpadeServer;

/**
 * Hello world!
 *
 */
public class App {
    public static final String HOST = "http://localhost:8888/";

    public static final Scheduler<String> SCHEDULER = getScheduler();

    public static final Redis redis = getRedis();

    public static Scheduler<String> getScheduler() {
        if (SCHEDULER == null) {
            return new Scheduler<String>();
        }
        return SCHEDULER;
    }

    public static Redis getRedis() {
        if (redis == null) {
            try {
                return new Redis();
            } catch (ConnectException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, "Redis connection error", ex);
            }
        }
        return redis;
    }

    public static void main(String[] args) throws Exception {
        String host = HOST;
        if (args.length > 1) {
            host = args[0];
        }

        AtmosphereSpadeServer setup =
            WebServerConfigurator.setup(host);
        setup.start();
        new ScheduleProcessor(SCHEDULER, getRedis()).process();
    }
}
