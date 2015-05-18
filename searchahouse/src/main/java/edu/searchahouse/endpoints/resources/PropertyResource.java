
package edu.searchahouse.endpoints.resources;

import org.springframework.hateoas.Resource;

import edu.searchahouse.model.Property;

public class PropertyResource extends Resource<Property> {

	public PropertyResource(final Property content) {
		super(content);
	}

}
