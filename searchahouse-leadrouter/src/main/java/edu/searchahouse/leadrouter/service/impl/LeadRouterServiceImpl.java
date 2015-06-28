package edu.searchahouse.leadrouter.service.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.searchahouse.leadrouter.exceptions.LeadRouterException;
import edu.searchahouse.leadrouter.model.Agent;
import edu.searchahouse.leadrouter.model.Lead;
import edu.searchahouse.leadrouter.model.Lead.Status;
import edu.searchahouse.leadrouter.service.LeadRouterService;

@Service
public class LeadRouterServiceImpl implements LeadRouterService {

    private final RestTemplate restTemplate;

    @Autowired
    public LeadRouterServiceImpl(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String routeLead(Lead lead, final String propertyId) {

        String endpointGetAgentsWithProperty = "http://localhost:8080/api/v1/agent/property/" + propertyId;

        ResponseEntity<PagedResources<Resource<Agent>>> pagedResourceResponse = this.restTemplate.exchange(endpointGetAgentsWithProperty, HttpMethod.GET, null,
                new ParameterizedTypeReference<PagedResources<Resource<Agent>>>() {
                });

        Collection<Agent> agents = pagedResourceResponse.getBody().getContent().stream().map(Resource::getContent).collect(Collectors.toList());

        // find the agent id which has the minimum "UNCONTACTED" leads.
        //@formatter:off
        Optional<String> agentId = agents.stream()
                .sorted( (e1, e2) -> e1.getLeads().stream()
                                            .filter(l -> l.getContactStatus() == Status.CONTACTED)
                                            .count() 
                                            < 
                                     e2.getLeads().stream()
                                     .filter(l -> l.getContactStatus() == Status.CONTACTED)
                                     .count() 
                                     ? -1 : 1 
                         )
                .findFirst()
                .map( Agent::getPrimaryKey );
      //@formatter:on

        // assign the lead to the agent.
        String endpointAddLeadToAgent = "http://localhost:8080/api/v1/agent/" + agentId.get() + "/lead";

        this.restTemplate.setMessageConverters(Arrays.asList(new MappingJackson2HttpMessageConverter()));

        ResponseEntity<?> response = this.restTemplate.postForEntity(endpointAddLeadToAgent, lead, ResponseEntity.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new LeadRouterException("Could not assign lead to an agent.");
        }
        
        return response.getHeaders().getLocation().toString();
    }

}
