package edu.searchahouse.web.service.impl;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.searchahouse.web.model.Agent;
import edu.searchahouse.web.service.AgentService;

@Service
public class AgentServiceImpl implements AgentService {

    private final RestTemplate restTemplate;

    @Autowired
    public AgentServiceImpl(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Collection<Agent> findTopAgents(int n) {
        
        String endpoint = "http://localhost:8080/api/v1/agent";
        
        ResponseEntity<PagedResources<Resource<Agent>>> pagedResourceResponse = this.restTemplate.exchange(endpoint, HttpMethod.GET, null,
                new ParameterizedTypeReference<PagedResources<Resource<Agent>>>() {
                });

        Collection<Agent> agents = pagedResourceResponse.getBody().getContent().stream().map(Resource::getContent).collect(Collectors.toList());

        return agents;
    }

    // @Override
    // public Agent findById(String id) {
    //
    // Agent agent = new Agent("gustavoooo", "1");
    //
    // Collection<Property> properties = new ArrayList<Property>();
    // properties.add( new Property("House 1", "Located in beverly Hills", PropertyType.RENT) );
    // properties.add( new Property("House 2", "Located in New York", PropertyType.SALE) );
    // properties.add( new Property("House 3", "Located in Miami", PropertyType.RENT) );
    // properties.add( new Property("House 4", "Located in Las Vegas", PropertyType.RENT) );
    //
    // agent.setProperties(properties);
    //
    // return agent;
    // }

    @Override
    public Agent findById(final String id) {

        String endpoint = "http://localhost:8080/api/v1/agent/" + id;

        ResponseEntity<Resource<Agent>> resourceResponse = this.restTemplate.exchange(endpoint, HttpMethod.GET, null,
                new ParameterizedTypeReference<Resource<Agent>>() {
                });

        Agent agent = resourceResponse.getBody().getContent();

        return agent;
    }

}
