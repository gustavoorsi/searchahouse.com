package edu.searchahouse.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.searchahouse.model.Agent;

public interface AgentService {

	public Agent findAgentById(String id);

	public Page<Agent> getAgentsByPage(Pageable pageable);

	public Agent save(Agent input);

	public Agent update(final String agentId, Agent input);

}
