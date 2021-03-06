package edu.searchahouse.web.model;

import java.util.ArrayList;
import java.util.Collection;

//@formatter:off
/**
 * 
 * An agent is someone that have properties to sell/rent and answer all inquiries by the leads.
 * 
 * For mongodb embedded documents or ref documents:
 * --- As a general rule, if you have a lot of "comments" or if they are large, a separate collection might be best. Smaller and/or fewer documents tend to be a
 * --- natural fit for embedding.
 * 
 * @author Gustavo Orsi
 *
 */
//@formatter:on
public class Agent extends BaseEntity {

    private String imageUrl;
    private String firstName;
    private String lastName;
    private String email;
    private Collection<Property> properties;
    private Collection<Lead> leads;

    public Agent() {
    }

    public Agent(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public Collection<Lead> getLeads() {
        return leads;
    }

    public void setLeads(Collection<Lead> leads) {
        this.leads = leads;
    }

    public Collection<Property> addProperty(final Property aProperty) {
        if (getProperties() == null) {
            setProperties(new ArrayList<Property>());
        }
        getProperties().add(aProperty);
        return getProperties();
    }

    public Collection<Lead> addLead(final Lead lead) {
        if (getLeads() == null) {
            setLeads(new ArrayList<Lead>());
        }
        getLeads().add(lead);
        return getLeads();
    }
}
