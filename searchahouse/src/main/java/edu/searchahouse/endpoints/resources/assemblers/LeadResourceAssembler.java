package edu.searchahouse.endpoints.resources.assemblers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import edu.searchahouse.endpoints.LeadRestEndpoint;
import edu.searchahouse.endpoints.resources.LeadResource;
import edu.searchahouse.model.Lead;

@Component
public class LeadResourceAssembler implements ResourceAssembler<Lead, LeadResource> {

	@Override
	public LeadResource toResource(Lead entity) {

		LeadResource pr = new LeadResource(entity);

		// add link to itself ( rel = self )
		Link selfLink = linkTo(methodOn(LeadRestEndpoint.class).getLead(entity.getId().toString())).withSelfRel();
		pr.add(selfLink);

		return pr;
	}

}
