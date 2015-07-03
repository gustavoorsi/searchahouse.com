package edu.searchahouse.admin.model;

/**
 * 
 * This is a regular person who wants information about a property. Basically a lead is a possible buyer of a property.
 * 
 * @author Gustavo Orsi
 *
 */
public class Lead {

	private String primaryKey;
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

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
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

	public enum Status {
		CONTACTED, UNCONTACTED;
	}

}
