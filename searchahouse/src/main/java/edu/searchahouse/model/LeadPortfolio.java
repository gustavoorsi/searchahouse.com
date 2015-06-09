package edu.searchahouse.model;

import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 * Store the lead that the agent has to contact and a status (whether the lead had been contacted or not).
 * 
 * @author Gustavo Orsi
 *
 */
public class LeadPortfolio {

	public enum Status {
		CONTACTED, UNCONTACTED;
	}

	private Status contactStatus = Status.UNCONTACTED;

	@DBRef
	private Lead lead;

	public LeadPortfolio() {
	}

	public LeadPortfolio(Lead lead) {
		this.lead = lead;
	}

	public Status getContactStatus() {
		return contactStatus;
	}

	public void setContactStatus(Status contactStatus) {
		this.contactStatus = contactStatus;
	}

	public Lead getLead() {
		return lead;
	}

	public void setLead(Lead lead) {
		this.lead = lead;
	}

}
