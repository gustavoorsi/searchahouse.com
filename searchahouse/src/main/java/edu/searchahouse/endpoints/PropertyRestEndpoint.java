package edu.searchahouse.endpoints;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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

	/**
	 * ----------------------------------------------------------------------------------------------------------------
	 * 
	 * GET - find "Pageable" properties
	 * 
	 * ----------------------------------------------------------------------------------------------------------------
	 * 
	 * Return a pageable list of Properties.
	 * 
	 * @param pageable
	 *            the page data. Page number and page size.
	 * @param assembler
	 *            the assembler that will construct the property resource as a pageable resource.
	 * @return A pageable list of properties in json or xml format (default to json)
	 * 
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public HttpEntity<PagedResources<PropertyResource>> getPropertiesByPage( //
			@PageableDefault(size = 10, page = 0) Pageable pageable, //
			PagedResourcesAssembler<Property> assembler //
	) {

		Page<Property> properties = this.propertyService.getPropertiesByPage(pageable);

		return new ResponseEntity<PagedResources<PropertyResource>>(assembler.toResource(properties, this.propertyResourceAssembler), HttpStatus.OK);

	}

	/**
	 *
	 * ----------------------------------------------------------------------------------------------------------------
	 * 
	 * GET - find a Property by id
	 * 
	 * ----------------------------------------------------------------------------------------------------------------
	 * 
	 * Get a property by it's primary key. Throw 404 if not found.
	 * 
	 * @param propertyId
	 * @return A property in json or xml format (default to json).
	 * 
	 */
	@RequestMapping(value = "/{propertyId}", method = RequestMethod.GET)
	public HttpEntity<PropertyResource> getProperty(@PathVariable String propertyId) {
		Property aProperty = this.propertyService.findPropertyById(propertyId);

		return new ResponseEntity<PropertyResource>(this.propertyResourceAssembler.toResource(aProperty), HttpStatus.OK);
	}

	/**
	 * ----------------------------------------------------------------------------------------------------------------
	 * 
	 * POST - Create a property
	 * 
	 * ----------------------------------------------------------------------------------------------------------------
	 * 
	 * Create a new property. Throw 422 if resource already exist.
	 * 
	 * @param propertyId
	 * @return 201 Created and the property location. 422 if the property already exist.
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public HttpEntity<?> createProperty(@RequestBody Property input) {

		Property property = this.propertyService.save(input);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(linkTo(methodOn(PropertyRestEndpoint.class, property.getId()).getProperty(property.getId())).toUri());

		return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
	}

	/**
	 * ----------------------------------------------------------------------------------------------------------------
	 * 
	 * PUT - Update a property
	 * 
	 * ----------------------------------------------------------------------------------------------------------------
	 * 
	 * Updates an existing property. Throw 402 if resource does not exist or 403 in case the resource exist but could not be updated.
	 * 
	 * @param propertyId
	 * @return 204 if updated ok, 402 if resource does not exist or 403 in case the resource exist but could not be updated.
	 */
	@RequestMapping(value = "/{propertyId}", method = RequestMethod.PUT)
	public HttpEntity<?> updateProperty( //
			@RequestBody Property input,//
			@PathVariable String propertyId //
	) {

		Property property = this.propertyService.update(propertyId, input);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(linkTo(methodOn(PropertyRestEndpoint.class, property.getId()).getProperty(property.getId())).toUri());

		return new ResponseEntity<>("The resource was updated ok.", httpHeaders, HttpStatus.NO_CONTENT);
	}

}
