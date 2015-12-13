package kkmashup.ivr.routefinder;

/**
 *
 * @author Arun Kumar
 */
public class Route {
private String bus_nos;
private String distance;
private String Hop;
private String From;
private String duration;
private String To;

    /**
     * @return the busNos
     */
    public String getBusNos() {
        return bus_nos;
    }

    /**
     * @param busNos the busNos to set
     */
    public void setBusNos(String busNos) {
        this.bus_nos = busNos;
    }

    /**
     * @return the distance
     */
    public String getDistance() {
        return distance;
    }

    /**
     * @param distance the distance to set
     */
    public void setDistance(String distance) {
        this.distance = distance;
    }

    /**
     * @return the Hop
     */
    public String getHop() {
        return Hop;
    }

    /**
     * @param Hop the Hop to set
     */
    public void setHop(String Hop) {
        this.Hop = Hop;
    }

    /**
     * @return the From
     */
    public String getFrom() {
        return From;
    }

    /**
     * @param From the From to set
     */
    public void setFrom(String From) {
        this.From = From;
    }

    /**
     * @return the duration
     */
    public String getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     * @return the To
     */
    public String getTo() {
        return To;
    }

    /**
     * @param To the To to set
     */
    public void setTo(String To) {
        this.To = To;
    }
    
    @Override
    public String toString()
    {
        return " From:" + getFrom() + 
                " To:" + getTo() +
                " Bus number:" + getBusNos() +
                " total distance:" + getDistance();
    }
}
