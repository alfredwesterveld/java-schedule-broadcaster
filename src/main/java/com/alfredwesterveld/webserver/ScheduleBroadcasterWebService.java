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

/**
 *
 * @author alfred
 */
@Singleton
@Path("/scheduler")
public class ScheduleBroadcasterWebService {
    private static Scheduler<String> tasks = App.getScheduler();

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("schedule")
    public String schedule(MultivaluedMap<String, String> form) {
        String epochStr = form.getFirst("epoch");
        String message = form.getFirst("message");

        try {
            long epoch = Long.parseLong(epochStr);
            message.equals("");
            tasks.schedule(new Task<String>(message, epoch));
        } catch(Exception e) {
            ResponseBuilder status = Response.status(Status.BAD_REQUEST);
            Response build = status.entity("Parameter(s) incorrect\n").build();
            throw new WebApplicationException(build);
        }
        return "+OK\n";
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Path("remove")
    public String remove(MultivaluedMap<String, String> form) {
        String epochStr = form.getFirst("epoch");
        String message = form.getFirst("message");
        try {
            long epoch = Long.parseLong(epochStr);
            message.equals("");
            boolean remove = tasks.remove(new Task<String>(message, epoch));
            return Boolean.toString(remove) + "\n";
        } catch(Exception e) {
            ResponseBuilder status = Response.status(Status.BAD_REQUEST);
            Response build = status.entity("Parameter(s) incorrect\n").build();
            throw new WebApplicationException(build);
        }
    }

    @GET
    @Path("stats")
    public String stats() {
        return Integer.toString(tasks.getTotalTaskScheduled()) + "\n";
    }
}
