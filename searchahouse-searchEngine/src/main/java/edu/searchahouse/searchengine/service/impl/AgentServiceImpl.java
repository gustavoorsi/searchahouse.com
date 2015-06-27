package edu.searchahouse.searchengine.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import edu.searchahouse.searchengine.model.Agent;
import edu.searchahouse.searchengine.persistence.repository.elasticsearch.AgentRepository;
import edu.searchahouse.searchengine.service.AgentService;

@Service
public class AgentServiceImpl implements AgentService {

    private final AgentRepository agentRepository;

    private final ElasticsearchOperations elasticsearchOperations;

    @Autowired
    public AgentServiceImpl(//
            final AgentRepository agentRepository, //
            final ElasticsearchOperations elasticsearchOperations //
    ) {
        this.agentRepository = agentRepository;
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    public List<Agent> findAgentsByFirstName(final String autocompleteFirstName) {

        List<Agent> agents = this.agentRepository.findAutocompleteAgentsByFirstName(autocompleteFirstName);

        return agents;
    }
    
    

    @Override
	public Page<Agent> findAll(Pageable pageable) {
    	
    	Page<Agent> agents = this.agentRepository.findAll(pageable);
    	
		return agents;
	}

	@Override
    public Agent findAgentByEmail(String email) {

        Agent agent = this.agentRepository.findByEmail(email);

        return agent;
    }

	@Override
	public Agent findAgentByPrimaryKey(String id) {
		
		Agent agent = this.agentRepository.findOne(id);
		
		// TODO: find a way to exclude nested list from agent (properties, leads) in the repository layer.
		agent.setProperties(null);
		agent.setLeads(null);

        return agent;
	}
	
	

}
