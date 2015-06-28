package edu.searchahouse.leadrouter.service;

import edu.searchahouse.leadrouter.model.Lead;

public interface LeadRouterService {

    /**
     * Route a lead to an agent. We get a list of possible agents that handle that property.
     * 
     * @param lead
     * @param propertyId
     */
    void routeLead(Lead lead, final String propertyId);

}
