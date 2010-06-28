/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.alfredwesterveld.webserver;

import org.atmosphere.grizzly.AtmosphereSpadeServer;

/**
 *
 * @author alfred
 */
public class WebServerConfigurator {
    public static AtmosphereSpadeServer setup(String host) {
        String resourcePackage = 
            com.alfredwesterveld.webserver.
                ScheduleBroadcasterWebService.class.getPackage().getName();
        AtmosphereSpadeServer build = 
                AtmosphereSpadeServer.build(host, resourcePackage);
        return build;
    }
}
