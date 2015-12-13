/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkmashup.ivr.demo;

import com.ozonetel.kookoo.Response;
import com.ozonetel.kookoo.TestClient;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;

/**
 * REST Web Service
 *
 * @author Arun Kumar
 */
@Path("inboundsms")
public class Inboundsms {

    @Context
    private UriInfo context;

    @Context
    private ServletContext servletContext;

    /**
     * Creates a new instance of Inboundsms
     */
    public Inboundsms() {
    }

    /**
     * Retrieves representation of an instance of kkmashup.ivr.demo.Inboundsms
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public void doGetInboundSms(@Context HttpServletRequest request, @Context HttpServletResponse response)
            throws IOException {

        response.setContentType("text/xml;charset=UTF-8");
        String event = request.getParameter("event");
        String the_number_which_sent_the_sms = request.getParameter("cid");
        String data = request.getParameter("data");
        String data1 = request.getParameter("data1");

        Response r = new Response(); //create kookoo Response Object

        String kookooResponseOutput = r.getXML();
        try {
            PrintWriter out = response.getWriter();
            out.println(kookooResponseOutput);
            out.flush();
        } catch (Exception ex) {
            Logger.getLogger(TestClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * PUT method for updating or creating an instance of Inboundsms
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
