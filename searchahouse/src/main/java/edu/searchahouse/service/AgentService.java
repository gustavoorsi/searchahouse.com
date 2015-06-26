package edu.searchahouse.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.searchahouse.model.Agent;
import edu.searchahouse.model.LeadPortfolio;
import edu.searchahouse.model.Property;

public interface AgentService {

	public Agent findAgentById(String id);
	
	public Agent findAgentByPropertyId(String propertyId);

	public Page<Agent> getAgentsByPage(Pageable pageable);

	public Agent save(Agent input);

	public Agent update(final String agentId, Agent input);
	
	public void updateLeadContactStatus( final String agentId, final String leadId, LeadPortfolio leadPortfolio );

	public Property addProperty(final String agentId, final String propertyId);

}
