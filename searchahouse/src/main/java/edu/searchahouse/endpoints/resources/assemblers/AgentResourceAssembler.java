package edu.searchahouse.endpoints.resources.assemblers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import edu.searchahouse.endpoints.AgentRestEndpoint;
import edu.searchahouse.endpoints.resources.AgentResource;
import edu.searchahouse.model.Agent;

@Component
public class AgentResourceAssembler implements ResourceAssembler<Agent, AgentResource> {

	@Override
	public AgentResource toResource(Agent entity) {

		AgentResource pr = new AgentResource(entity);

		// add link to itself ( rel = self )
		Link selfLink = linkTo(methodOn(AgentRestEndpoint.class).getAgent(entity.getId().toString())).withSelfRel();
		pr.add(selfLink);

		return pr;
	}

}
