package edu.searchahouse.searchengine.model;

import org.springframework.data.elasticsearch.annotations.Document;

/**
 * 
 * This is a regular person who wants information about a property. Basically a lead is a possible buyer of a property.
 * 
 * @author Gustavo Orsi
 *
 */
@Document(indexName = "searchahouse", type = "lead")
public class Lead extends BaseEntity {

    private Status contactStatus = Status.UNCONTACTED;
    private String firstName;
    private String lastName;
    private String email;
    private String mobilePhone;

    public Lead() {
    }

    public Lead(String firstName, String lastName, String email, String mobilePhone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobilePhone = mobilePhone;
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

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public enum Status {
        CONTACTED, UNCONTACTED;
    }

}
