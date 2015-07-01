package edu.searchahouse.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.searchahouse.admin.model.Agent;

public interface AgentService {

	Page<Agent> findAllAgents(final Pageable pageable);
	
	void deleteAgent( final String agentId );

}
