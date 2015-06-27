package edu.searchahouse.endpoints.resources.assemblers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Component;

import edu.searchahouse.endpoints.PropertyRestEndpoint;
import edu.searchahouse.model.Property;

@Component
public class PropertyResourceAssembler implements ResourceAssembler<Property, ResourceSupport> {

	@Override
    public ResourceSupport toResource(Property entity) {

		// add link to itself ( rel = self )
		Link selfLink = linkTo(methodOn(PropertyRestEndpoint.class).getProperty(entity.getPrimaryKey().toString())).withSelfRel();
		entity.add(selfLink);

		return entity;
	}

}
