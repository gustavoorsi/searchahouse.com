package edu.searchahouse.admin.model;

//@formatter:off
/**
 * 
 * An agent is someone that have properties to sell/rent and answer all inquiries by the leads.
 * 
 * @author Gustavo Orsi
 *
 */
//@formatter:on
public class Agent {

	private String primaryKey;
	private String imageUrl;
	private String firstName;
	private String lastName;
	private String email;

	public Agent() {
	}

	public Agent(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
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
	
	public String getFullName(){
		return this.firstName + " " + this.lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
