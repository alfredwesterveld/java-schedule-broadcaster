package com.alfredwesterveld;

import com.alfredwesterveld.webserver.WebServerConfigurator;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import java.util.concurrent.Future;
import org.atmosphere.grizzly.AtmosphereSpadeServer;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTest {
    public static final String HOST = "http://localhost:8888/";
    
    /**
     * 
     * @throws Exception
     */
    @Test @Ignore
    public void testSimpleResource() throws Exception {
        System.out.println("vaag in het kwadraat");
        AtmosphereSpadeServer setup = 
            WebServerConfigurator.setup(HOST);
        setup.start();
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        Future<Response> execute = 
            asyncHttpClient.prepareGet(HOST + "test").execute();
        assertEquals("scheduler", execute.get().getResponseBody());
    }

    @Test
    public void simpleTest() throws Exception {
        assertTrue(true);
    }

    @Test
    public void three() throws Exception {
        System.out.println("three times is blaat");
    }
}
