package edu.searchahouse.searchengine.persistence.repository.elasticsearch;

import java.util.List;
import java.util.Optional;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import edu.searchahouse.searchengine.model.Agent;

public interface AgentRepository extends ElasticsearchRepository<Agent, String> {

    @Query(name = "autocomplete_firstName", value = "{\"match\" : { \"firstName.autocomplete\" : { \"query\" : \"?0\", \"analyzer\" : \"standard\" } } }")
    public List<Agent> findAutocompleteAgentsByFirstName(final String autocompleteFirstName);

    public Agent findByEmail(final String email);
    
    public Optional<Agent> findByPrimaryKey( final String agentPrimaryKey );

}
