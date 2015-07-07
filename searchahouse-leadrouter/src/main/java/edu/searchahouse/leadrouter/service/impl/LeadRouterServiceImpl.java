package edu.searchahouse.leadrouter.service.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.searchahouse.leadrouter.exceptions.LeadRouterException;
import edu.searchahouse.leadrouter.model.Agent;
import edu.searchahouse.leadrouter.model.Lead;
import edu.searchahouse.leadrouter.model.Lead.Status;
import edu.searchahouse.leadrouter.service.LeadRouterService;

@Service
public class LeadRouterServiceImpl implements LeadRouterService {

    // private final MappingJackson2HttpMessageConverter converter;
    //
    // @Autowired
    // public LeadRouterServiceImpl(final MappingJackson2HttpMessageConverter converter) {
    // this.converter = converter;
    // }

    /**
     * 
     * Add a lead to the agent based on a simple algorithm. Choose the agent with less uncontacted leads.
     * 
     * @param lead
     * @param propertyId
     * @return
     */
    @Override
    public String routeLead(Lead lead, final String propertyId) {

        // convert paged list into a collection.
        Collection<Agent> agents = findAgentsForProperty(propertyId);

        // find the agent id which has the minimum "UNCONTACTED" leads.
        //@formatter:off
        Optional<String> agentId = agents.stream()
                .sorted( (e1, e2) -> e1.getLeads().stream()
                                            .filter(l -> l.getContactStatus() == Status.CONTACTED)
                                            .count() 
                                            > 
                                     e2.getLeads().stream()
                                     .filter(l -> l.getContactStatus() == Status.CONTACTED)
                                     .count() 
                                     ? -1 : 1 
                         )
                .findFirst()
                .map( Agent::getPrimaryKey );
        //@formatter:on

        // post new lead to the agent.
        ResponseEntity<?> response = postLeadToAgent(lead, agentId);

        return response.getHeaders().getLocation().toString();
    }

    /**
     * 
     * Find all agents that has the property assigned.
     * 
     * @param propertyId
     * @return
     */
    private Collection<Agent> findAgentsForProperty(final String propertyId) {
        // get a new instance of restTemplate and set the jackson2 message converter (which supports hal format).

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new Jackson2HalModule()); // support hal+json

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);
        RestTemplate restTemplate = new RestTemplate(Arrays.asList(converter));

        // RestTemplate restTemplate = new RestTemplate(Arrays.asList(this.converter));

        String endpointGetAgentsWithProperty = "http://localhost:8080/api/v1/agent/property/" + propertyId;

        // make rest call and get a list of agents that have this property assigned.
        ResponseEntity<PagedResources<Resource<Agent>>> pagedResourceResponse = restTemplate.exchange(endpointGetAgentsWithProperty, HttpMethod.GET, null,
                new ParameterizedTypeReference<PagedResources<Resource<Agent>>>() {
                });

        // convert paged list into a collection.
        Collection<Agent> agents = pagedResourceResponse.getBody().getContent().stream().map(Resource::getContent).collect(Collectors.toList());

        return agents;
    }

    /**
     * Add the lead to the agent.
     * 
     * @param lead
     * @param agentId
     * @return
     */
    private ResponseEntity<?> postLeadToAgent(final Lead lead, final Optional<String> agentId) {

        // get a new instance of restTemplate and set the jackson2 message converter (which does not supports hal format).
        // NOTE: The jackson2 message converter we used before had an object mapper that support hal format (which was ok for GET the list of agents because
        // they are returned in hal format from other microservice) but here we are sending (POST) in plain json (not hal) format.
        RestTemplate restTemplate = new RestTemplate(Arrays.asList(new MappingJackson2HttpMessageConverter()));

        // assign the lead to the agent.
        String endpointAddLeadToAgent = "http://localhost:8080/api/v1/agent/" + agentId.get() + "/lead";

        // post new lead to the agent.
        ResponseEntity<?> response = null;
        try {
            response = restTemplate.postForEntity(endpointAddLeadToAgent, lead, ResponseEntity.class);
        } catch (HttpClientErrorException e) {
            throw new LeadRouterException(e, "Could not assign lead to an agent. " + e.getResponseBodyAsString());
        }

        return response;
    }

}
