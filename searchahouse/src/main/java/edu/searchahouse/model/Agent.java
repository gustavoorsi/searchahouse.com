package edu.searchahouse.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;

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
@TypeAlias("Agent") // Customizing type mapping to avoid writing the entire Java class name as type information in mongodb ("_class" : "du.searchahouse.model.Agent").
//@formatter:on
public class Agent extends BaseEntity {

	@NotNull( message = "First Name can not be empty." )
	private String firstName;

	@NotNull( message = "Last Name can not be empty." )
	private String lastName;

	@NotNull( message = "Email can not be empty." )
	@Email( message = "Not a valid email." )
	@Indexed(unique = true)
	private String email;

	private Collection<Property> properties;

	private Collection<LeadPortfolio> leads;

	public Agent() {
	}

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

	public Collection<LeadPortfolio> getLeads() {
		return leads;
	}

	public void setLeads(Collection<LeadPortfolio> leads) {
		this.leads = leads;
	}

	public Collection<Property> addProperty(final Property aProperty) {
		if (getProperties() == null) {
			setProperties(new ArrayList<Property>());
		}
		getProperties().add(aProperty);
		return getProperties();
	}

	public Collection<LeadPortfolio> addLead(final LeadPortfolio lead) {
		if (getLeads() == null) {
			setLeads(new ArrayList<LeadPortfolio>());
		}
		getLeads().add(lead);
		return getLeads();
	}

	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		put(map, this.email, "email");
		put(map, this.firstName, "firstName");
		put(map, this.lastName, "lastName");
		put(map, this.properties, "properties");

		return map;
	}

}
