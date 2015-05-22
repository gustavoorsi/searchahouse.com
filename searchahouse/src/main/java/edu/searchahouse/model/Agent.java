
package edu.searchahouse.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * An agent is someone that have properties to sell/rent and answer all inquiries by the
 * leads.
 * 
 * @author Gustavo Orsi
 *
 */
public class Agent extends BaseEntity {

	private String firstName;

	private String lastName;

	private String email;

	private Collection<Property> properties;

	public Agent(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
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

	public Collection<Property> addProperty(final Property aProperty) {
		if (getProperties() == null) {
			setProperties(new ArrayList<Property>());
		}
		getProperties().add(aProperty);
		return getProperties();
	}
	
	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		put(map, this.email, "email");
		put(map, this.firstName, "firstName");
		put(map, this.lastName, "lastName");

		return map;
	}

}
