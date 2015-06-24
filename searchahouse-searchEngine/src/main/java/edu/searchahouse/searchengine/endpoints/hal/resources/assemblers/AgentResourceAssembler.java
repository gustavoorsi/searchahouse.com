package edu.searchahouse.searchengine.endpoints.hal.resources.assemblers;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import edu.searchahouse.searchengine.endpoints.hal.resources.AgentResource;
import edu.searchahouse.searchengine.model.Agent;

@Component
public class AgentResourceAssembler implements ResourceAssembler<Agent, AgentResource> {

	@Override
	public AgentResource toResource(Agent entity) {

		AgentResource pr = new AgentResource(entity);

		// add link to itself ( rel = self )
		// TODO: get the endpoint url that GET a property from micro-service CRUD. (using eureka). For now just hardcode the domain.
		// add link to itself ( rel = self )
		Link selfLink = new Link("http://localhost:8081/api/v1/agent/" + entity.getId()).withRel("self");
		pr.add(selfLink);

		return pr;
	}

}
