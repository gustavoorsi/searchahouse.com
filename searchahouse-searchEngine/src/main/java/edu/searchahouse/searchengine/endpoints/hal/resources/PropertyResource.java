
package edu.searchahouse.searchengine.endpoints.hal.resources;

import org.springframework.hateoas.Resource;

import edu.searchahouse.searchengine.model.Property;


public class PropertyResource extends Resource<Property> {

	public PropertyResource(final Property content) {
		super(content);
	}

}
