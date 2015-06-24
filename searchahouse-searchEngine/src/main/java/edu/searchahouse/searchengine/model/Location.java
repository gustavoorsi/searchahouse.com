package edu.searchahouse.searchengine.model;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

@Document(indexName = "searchahouse", type = "location")
public class Location extends BaseEntity {

    private String country;
    private String state;
    private String city;
    private GeoPoint geopoint;
    
    public Location(){}

    public Location(String country, String state, String city, GeoPoint geopoint) {
        this.country = country;
        this.state = state;
        this.city = city;
        this.geopoint = geopoint;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public GeoPoint getGeopoint() {
        return geopoint;
    }

    public void setGeopoint(GeoPoint geopoint) {
        this.geopoint = geopoint;
    }

}
