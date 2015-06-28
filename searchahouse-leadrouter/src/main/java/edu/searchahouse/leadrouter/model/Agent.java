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

    private Collection<Lead> leads;

    public Agent() {
    }

    public Collection<Lead> getLeads() {
        return leads;
    }

}
