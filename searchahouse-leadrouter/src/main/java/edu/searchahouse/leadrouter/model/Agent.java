package edu.searchahouse.leadrouter.model;

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

	private String firstName;

	private String lastName;

	private String email;

	private String imageUrl;

	private Collection<Property> properties;

	private Collection<Lead> leads;

	public Agent() {
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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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

}
