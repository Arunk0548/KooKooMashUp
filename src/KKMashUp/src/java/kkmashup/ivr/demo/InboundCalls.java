package kkmashup.ivr.demo;

import com.ozonetel.kookoo.Record;
import com.ozonetel.kookoo.Response;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import kkmashup.ivr.audiocodecc.SpeechToText;
import kkmashup.ivr.routefinder.GetRouteDetails;
import kkmashup.ivr.routefinder.Routes;

/**
 * REST Web Service
 *
 * @author Arun Kumar
 */
@Path("inboundcalls")
public class InboundCalls {

    @Context
    private UriInfo context;

    @Context
    private ServletContext servletContext;

    /**
     * Creates a new instance of InboundCalls
     */
    public InboundCalls() {
    }

    /**
     * Retrieves representation of an instance of kkmashup.ivr.demo.InboundCalls
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/xml")
    public void doGetHandleInboundCall(@Context HttpServletRequest request, @Context HttpServletResponse response)
            throws IOException {

        response.setContentType("text/xml;charset=UTF-8");

        String event = request.getParameter("event");

        Response r = new Response(); //create kookoo Response Object

        if ((null != event) && event.equalsIgnoreCase("NewCall")) {
            r.addPlayText(Prompts.Welcome); // add play text object
            r.addPlayText("Please say your message to find bus route like majestic to Electronic city after the beep");
            Record rec = new Record();
            rec.setFormat("wav");
            rec.setSilence(3);
            rec.setFileName(SpeechToText.getAudioFileName());
            rec.setMaxDuration(8);
            r.addRecord(rec);

        } else if ((null != event) && event.equalsIgnoreCase("NewSMS")) {
            OutboundSms sms = new OutboundSms();
            String the_number_which_sent_the_sms = request.getParameter("cid");
            String extractedText = request.getParameter("message");
            if (extractedText != null) {
                String[] srcDest = extractedText.split("to");
                if (srcDest.length == 2) {
                    GetRouteDetails details = new GetRouteDetails();
                    String source = details.check(srcDest[0].trim(), servletContext);
                    String destination = details.check(srcDest[1].trim(), servletContext);

                    if (source == null || destination == null) {
                        sms.sendSms("Ops! no match found from source to destination", the_number_which_sent_the_sms);
                    } else {
                        Routes route = details.findRoute(source, destination);
                        if (route == null) {
                            sms.sendSms("Ops! no route found for given source and destination", the_number_which_sent_the_sms);
                        } else {
                            String msg = Arrays.toString(route.getRoute1().toArray()) + "\nthank you! for using our service";
                            sms.sendSms(msg, the_number_which_sent_the_sms);
                        }

                    }
                } else {
                    sms.sendSms("Ops! unable to recognize : please try again! Recorded text :" + extractedText, the_number_which_sent_the_sms);

                }
            }

        } else if ((null != event) && event.equalsIgnoreCase("GotDTMF")) {
            

        } else if ((null != event) && event.equalsIgnoreCase("Record")) {
            SpeechToText txtToSph = new SpeechToText();
            try {
                Thread.sleep(4000);
            } catch (InterruptedException ex) {
                Logger.getLogger(InboundCalls.class.getName()).log(Level.SEVERE, null, ex);
            }
            String extractedText = txtToSph.getAuddioText(request.getParameter("data"), servletContext);

            if (extractedText == null || extractedText.isEmpty()) {
                r.addPlayText("Ops! unable to recognize : please try again!");                
                r.addHangup();
            } else {
                String[] srcDest = extractedText.split("to");
                if (srcDest.length == 2) {
                    GetRouteDetails details = new GetRouteDetails();
                    String source = details.check(srcDest[0].trim(), servletContext);
                    String destination = details.check(srcDest[1].trim(), servletContext);

                    if (source == null || destination == null) {
                        r.addPlayText("Ops! no match found from source to destination");
                    } else {
                        Routes route = details.findRoute(source, destination);
                        if (route == null) {
                            r.addPlayText("Ops! no route found for given source and destination");
                        } else {
                            r.addPlayText(Arrays.toString(route.getRoute1().toArray()));
                            r.addPlayText("thank you! for using our service");
                            r.addHangup();
                        }

                    }
                } else {
                    r.addPlayText("Ops! unable to recognize : please try again! Recorded text :" + extractedText);

                    r.addHangup();
                }
            }
        } else {
            r.addPlayText("call is disconnecting ");
            r.addHangup();
        }

        sendData(r, response);
    }

    private void sendData(Response r, HttpServletResponse response) {
        String kookooResponseOutput = r.getXML();
        try {
            PrintWriter out = response.getWriter();
            out.println(kookooResponseOutput);
            out.flush();
        } catch (Exception ignore) {
            System.out.println(ignore.toString());
        }
    }

    /**
     * PUT method for updating or creating an instance of InboundCalls
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
