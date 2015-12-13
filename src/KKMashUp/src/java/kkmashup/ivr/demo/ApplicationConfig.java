package kkmashup.ivr.demo;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Arun Kumar
 */
@javax.ws.rs.ApplicationPath("venturesity")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<Class<?>>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(kkmashup.ivr.demo.InboundCalls.class);
        resources.add(kkmashup.ivr.demo.Inboundsms.class);
    }
    
}
