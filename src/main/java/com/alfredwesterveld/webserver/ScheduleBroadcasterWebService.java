/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.alfredwesterveld.webserver;

import com.alfredwesterveld.App;
import com.alfredwesterveld.scheduler.Scheduler;
import com.alfredwesterveld.scheduler.Task;
import com.sun.jersey.spi.resource.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import org.atmosphere.annotation.Suspend;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.jersey.Broadcastable;
import org.atmosphere.jersey.JerseyBroadcaster;

/**
 *
 * @author alfred
 */
@Singleton
@Path("/scheduler")
public class ScheduleBroadcasterWebService {
    private static final Broadcaster scheduleBroadcaster =
        new JerseyBroadcaster("schedule");

    private static Scheduler<String> messages = App.getScheduler();

    @GET
    @Path("info")
    public String get() {
        return "scheduler";
    }

    @GET
    @Suspend(outputComments=false)
    @Path("")
    public Broadcastable getMessages() {
        return new Broadcastable(scheduleBroadcaster);
    }
    
    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("")
    public String post(MultivaluedMap<String, String> form) {
        String epochStr = form.getFirst("epoch");
        String message = form.getFirst("message");

        try {
            long epoch = Long.parseLong(epochStr);
            message.equals("");
            messages.schedule(new Task<String>(message, epoch));
        } catch(Exception e) {
            ResponseBuilder status = Response.status(Status.BAD_REQUEST);
            Response build = status.entity("Parameter(s) incorrect\n").build();
            throw new WebApplicationException(build);
        }
        return "+OK\n";
    }

    public static void broadcast(String message) {
        scheduleBroadcaster.broadcast(message + "\n");
    }
}
