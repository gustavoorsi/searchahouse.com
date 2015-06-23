package edu.searchahouse.searchengine.service;

import java.util.List;

import edu.searchahouse.searchengine.model.Agent;

public interface AgentService {

	List<Agent> findAgentsByFirstName(final String autocompleteFirstName);
	
	Agent findAgentByEmail( final String email );
	
}
