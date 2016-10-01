package pingproject.pingapp.CoreClasses;

/**
 * Created by SuperEEJ on 10/1/2016.
 */

public class Location {
    private Double Latitude;
    private Double Longitude;

    public Location(Double lat, Double longitude) {
        Latitude = lat;
        Longitude = longitude;
    }

    public Double getLat() {
        return Latitude;
    }

    public Double getLong() {
        return Longitude;
    }

    public void setLat(Double lat) {
        Latitude = lat;
    }

    public void setLong(Double longitude) {
        Longitude = longitude;
    }
}
