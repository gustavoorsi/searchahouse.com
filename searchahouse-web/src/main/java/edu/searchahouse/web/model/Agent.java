package edu.searchahouse.web.model;

import java.util.Collection;

public class Agent extends BaseEntity{

    private String firstName;
    private String lastName;
    private String email;
    private Collection<Property> properties;
    
    public Agent(){}

    public Agent(String firstName, String id) {
        this.firstName = firstName;
        setId(id);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<Property> getProperties() {
        return properties;
    }

    public void setProperties(Collection<Property> properties) {
        this.properties = properties;
    }

}
