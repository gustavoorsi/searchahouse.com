package edu.searchahouse.model;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * 
 * This is a regular person who wants information about a property. Basically a lead is a possible buyer of a property.
 * 
 * @author Gustavo Orsi
 *
 */
// Customizing type mapping to avoid writing the entire Java class name as type information in mongodb ("_class" : "du.searchahouse.model.Lead").
@TypeAlias("Lead")
public class Lead extends BaseEntity {

    private Status contactStatus = Status.UNCONTACTED;
    private String firstName;
    private String lastName;
    // sparse = true means that it will accept duplicates if the entity is inside a nested collection (for example, the leads collections in Agent class).
    @Indexed(unique = true, sparse = true)
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

    public Status getContactStatus() {
        return contactStatus;
    }

    public void setContactStatus(Status contactStatus) {
        this.contactStatus = contactStatus;
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

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        put(map, this.email, "email");
        put(map, this.firstName, "firstName");
        put(map, this.lastName, "lastName");
        put(map, this.mobilePhone, "mobilePhone");

        return map;
    }

    public enum Status {
        CONTACTED, UNCONTACTED;
    }

}
