package edu.searchahouse.web.service;

import java.util.Collection;

import edu.searchahouse.web.model.Agent;
import edu.searchahouse.web.model.Lead;

public interface AgentService {

	/**
	 * find the top 'n' agents based on the amount of properties attached.
	 * 
	 * @param n
	 *            the number of agents to return.
	 * @return a list of agents ordered desc on amount of properties associated.
	 */
	Collection<Agent> findTopAgents(final int n);

	/**
	 * Find an agent by id
	 * 
	 * @param id
	 * @return
	 */
	Agent findById(final String id);

	/**
	 * Find an agent by email
	 * 
	 * @param email
	 * @return
	 */
	Agent findByEmail(final String email);

	/**
	 * add a lead to an agent for a specific property. Guess the agent from the propertyId.
	 * 
	 * @param propertyId
	 */
	void addLeadToAgentForProperty(Lead lead, final String propertyId);

}
