package edu.searchahouse.searchengine.endpoints.hal.resources.assemblers;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import edu.searchahouse.searchengine.endpoints.hal.resources.PropertyResource;
import edu.searchahouse.searchengine.model.Property;

@Component
public class PropertyResourceAssembler implements ResourceAssembler<Property, PropertyResource> {

	@Override
	public PropertyResource toResource(Property entity) {

		PropertyResource pr = new PropertyResource(entity);

		// add link to itself ( rel = self )
		// TODO: get the endpoint url that GET a property from micro-service CRUD. (using eureka). For now just hardcode the domain.
		// add link to itself ( rel = self )
		Link selfLink = new Link("http://localhost:8081/api/v1/property/" + entity.getId()).withRel("self");
		pr.add(selfLink);

		return pr;
	}

}
