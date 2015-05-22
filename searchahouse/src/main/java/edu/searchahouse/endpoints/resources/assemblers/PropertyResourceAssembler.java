package edu.searchahouse.endpoints.resources.assemblers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import edu.searchahouse.endpoints.PropertyRestEndpoint;
import edu.searchahouse.endpoints.resources.PropertyResource;
import edu.searchahouse.model.Property;

@Component
public class PropertyResourceAssembler implements ResourceAssembler<Property, PropertyResource> {

	@Override
	public PropertyResource toResource(Property entity) {

		PropertyResource pr = new PropertyResource(entity);

		// add link to itself ( rel = self )
		Link selfLink = linkTo(methodOn(PropertyRestEndpoint.class).getProperty(entity.getId().toString())).withSelfRel();
		pr.add(selfLink);

		return pr;
	}

}
