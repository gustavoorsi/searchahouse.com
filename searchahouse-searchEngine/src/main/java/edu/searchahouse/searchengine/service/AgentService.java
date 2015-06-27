package edu.searchahouse.searchengine.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.searchahouse.searchengine.model.Agent;

public interface AgentService {

	List<Agent> findAgentsByFirstName(final String autocompleteFirstName);
	
	Page<Agent> findAll( final Pageable page );
	
	Agent findAgentByEmail( final String email );
	
	Agent findAgentByPrimaryKey( final String id );
	
}
