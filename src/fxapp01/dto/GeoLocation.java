package fxapp01.dto;

import fxapp01.excpt.EArgumentBreaksRule;
import fxapp01.log.ILogger;
import fxapp01.log.LogMgr;

/**
 *
 * @author serg
 */
public class GeoLocation {
    private final ILogger log = LogMgr.getLogger(this.getClass()); 
    private double longitude;
    private double latitude;
    
    public GeoLocation(double lat, double lon) {
        //широта от -90° до +90°, долгота от -180° до +180°
        check("constructor", lat, lon);
        this.latitude = lat;
        this.longitude = lon;
    }
    
    /* широта */
    public double getLatitude(){
        return latitude;
    }
    
    public void setLatitude(double lat){
        check("setLatitude", lat, longitude);
        this.latitude = lat;
    }
    
    /* долгота */
    public double getLongitude() {
        return longitude;
    }
    
    public void setLongitude(double lon){
        check("setLongitude", latitude, lon);
        this.longitude = lon;
    }
    
    private void check(String methodName, double lat, double lon) {
        //широта от -90° до +90°, долгота от -180° до +180°
        if (Math.abs(lat) > 90) {
            throw new EArgumentBreaksRule(methodName, "-90° <= Latitude <= 90°");
        }
        if (Math.abs(lon) > 180) {
            throw new EArgumentBreaksRule(methodName, "-180° <= Longitude <= 180°");
        }
    }
    
    /* расстояние в метрах до указанной точки
    http://en.wikipedia.org/wiki/Great-circle_distance
    http://www.java2s.com/Code/Java/2D-Graphics-GUI/Aclasstorepresentalatitudeandlongitude.htm
    http://introcs.cs.princeton.edu/java/44st/Location.java.html
    */
    public double distanceTo(GeoLocation that) {
        log.trace("distanceTo("+that+")");
        double lat1 = Math.toRadians(this.getLatitude());
        double lon1 = Math.toRadians(this.getLongitude());
        double lat2 = Math.toRadians(that.getLatitude());
        double lon2 = Math.toRadians(that.getLongitude());
        log.debug("In radians: lat1="+lat1+", lon1="+lon1+", lat2="+lat2+", lon2="+lon2);
        // Earth great circle distance in radians
        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
            + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
        log.debug("radian angle="+angle);
        // each degree on a great circle of Earth is 60 nautical miles
        // 1 nautical mile = 1852 m
        // 1852 m * (60 nautical mile / 1 angle degree) * angle = distance in meters of one angle degree on earth great circle
        double meters = 111120 * Math.toDegrees(angle); 
        log.debug("meters="+meters);
        return meters;
    }
    
    // return string representation of this point
    @Override
    public String toString() {
        return latitude + ", " + longitude;
    }
}