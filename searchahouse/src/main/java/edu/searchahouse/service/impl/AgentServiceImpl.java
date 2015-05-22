package edu.searchahouse.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import edu.searchahouse.exceptions.EntityNotFoundException;
import edu.searchahouse.model.Agent;
import edu.searchahouse.model.repository.mongo.AgentRepository;
import edu.searchahouse.service.AgentService;

@Service
public class AgentServiceImpl extends BaseService implements AgentService {

	private final AgentRepository agentRepository;

	@Autowired
	public AgentServiceImpl(//
			final AgentRepository agentRepository, //
			final MongoTemplate mongoTemplate//
	) {
		super(mongoTemplate);
		this.agentRepository = agentRepository;
	}

	@Override
	public Agent findAgentById(String id) {
		return this.agentRepository.findAgentById(id).orElseThrow(() -> new EntityNotFoundException("Agent"));
	}

	@Override
	public Page<Agent> getAgentsByPage(Pageable pageable) {
		return this.agentRepository.findAll(pageable);
	}

	@Override
	public Agent save(Agent input) {
		return this.agentRepository.save(input);
	}

	@Override
	public Agent update(String agentId, Agent input) {
		return (Agent) super.update( agentId, input );
	}


}
