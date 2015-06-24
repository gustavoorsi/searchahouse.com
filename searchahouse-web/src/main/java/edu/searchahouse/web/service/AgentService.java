package edu.searchahouse.web.service;

import java.util.Collection;

import edu.searchahouse.web.model.Agent;

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

}
