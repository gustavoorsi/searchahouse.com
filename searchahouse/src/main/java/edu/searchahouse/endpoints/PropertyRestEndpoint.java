
package edu.searchahouse.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.searchahouse.endpoints.resources.PropertyResource;
import edu.searchahouse.endpoints.resources.assemblers.PropertyResourceAssembler;
import edu.searchahouse.model.Property;
import edu.searchahouse.service.PropertyService;

@RestController
@RequestMapping("/api/v1/property")
public class PropertyRestEndpoint {

	// *************************************************************//
	// *********************** PROPERTIES **************************//
	// *************************************************************//
	private final PropertyService propertyService;

	private final PropertyResourceAssembler propertyResourceAssembler;

	// *************************************************************//
	// *********************** CONSTRUCTORS ************************//
	// *************************************************************//
	@Autowired
	public PropertyRestEndpoint(PropertyService propertyService, PropertyResourceAssembler propertyResourceAssembler) {
		this.propertyService = propertyService;
		this.propertyResourceAssembler = propertyResourceAssembler;
	}

	// *************************************************************//
	// ********************* REST ENDPOINTS ************************//
	// *************************************************************//

	@RequestMapping(value = "/{propertyId}", method = RequestMethod.GET)
	public HttpEntity<PropertyResource> getProperty(@PathVariable String propertyId) {
		Property aProperty = this.propertyService.findPropertyById(propertyId);

		return new ResponseEntity<PropertyResource>(this.propertyResourceAssembler.toResource(aProperty), HttpStatus.OK);
	}

}
