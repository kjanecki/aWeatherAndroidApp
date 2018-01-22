package com.marcinkaminski.aWeather.objects;

/**
 * Created by marcinkaminski on 19/09/2017.
 */
public class LocationPoints<Latitude, Longitude> {
    private final Latitude left;
    private final Longitude right;

    public LocationPoints(Latitude left, Longitude right) {
        this.left = left;
        this.right = right;
    }

    public Latitude getLatitude() { return left; }
    public Longitude getLongitude() { return right; }

    @Override
    public int hashCode() { return left.hashCode() ^ right.hashCode(); }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LocationPoints)) return false;
        LocationPoints pairo = (LocationPoints) o;
        return ( this.left.equals(pairo.getLatitude()) && this.right.equals(pairo.getLongitude()) );
    }

}
