package edu.searchahouse.web.model;

public class Address {

    private String state;
    private String street;

    public Address() {
    }

    public Address(String state, String street) {
        this.state = state;
        this.street = street;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

}
