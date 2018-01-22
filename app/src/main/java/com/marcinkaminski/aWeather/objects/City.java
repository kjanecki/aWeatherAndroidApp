package com.marcinkaminski.aWeather.objects;

/**
 * Created by marcinkaminski on 19/09/2017.
 */
public class City {
    private String name;
    private LocationPoints<Double,Double> coordinates;

    public void setName(String city) {
        this.name = city;
    }

    public String getName() {
        return name;
    }

    public LocationPoints<Double, Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double latitude, double longitude) {
        this.coordinates = new LocationPoints<>(latitude,longitude);
    }

    public City(String city, double latitude, double longitude) {
        this.name = city;
        setCoordinates(latitude,longitude);
    }


}
