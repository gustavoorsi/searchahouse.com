package edu.searchahouse.searchengine.endpoints;

import java.util.List;

import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.searchahouse.searchengine.endpoints.hal.resources.PropertyResource;
import edu.searchahouse.searchengine.endpoints.hal.resources.assemblers.PropertyResourceAssembler;
import edu.searchahouse.searchengine.model.Property;
import edu.searchahouse.searchengine.service.PropertyService;

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
	@RequestMapping(value = "/location", method = RequestMethod.GET)
	public HttpEntity<PagedResources<PropertyResource>> getPropertiesByPage( //
			@RequestParam(value = "longitude", required = true) final Double longitude, //
			@RequestParam(value = "latitude", required = true) final Double latitude, //
			@RequestParam(value = "distance", required = false, defaultValue = "1.0") final Double distance, //
			@RequestParam(value = "sortOrder", required = false, defaultValue = "DESC") final String sortOrderAsString, //
			PagedResourcesAssembler<Property> assembler //
	) {

		List<Property> properties = this.propertyService.findPropertiesByLocation(new GeoPoint(latitude, longitude), distance,
				SortOrder.valueOf(sortOrderAsString.toUpperCase()));

		return new ResponseEntity<PagedResources<PropertyResource>>(assembler.toResource(new PageImpl<Property>(properties), this.propertyResourceAssembler),
				HttpStatus.OK);

	}

}
