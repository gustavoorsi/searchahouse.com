package edu.searchahouse.searchengine.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    public Agent findAgentByEmail(String email) {

        Agent agent = this.agentRepository.findByEmail(email);

        return agent;
    }

}
