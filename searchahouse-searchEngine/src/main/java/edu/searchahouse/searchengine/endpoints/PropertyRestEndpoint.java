package edu.searchahouse.searchengine.endpoints;

import java.util.List;

import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
     * GET - find " property by id
     * 
     * ----------------------------------------------------------------------------------------------------------------
     * 
     * Return a property.
     * 
     * @param id
     *            the id to search for.
     * @return A property in json or xml format (default to json)
     * 
     */
    @RequestMapping(value = "/{propertyId}", method = RequestMethod.GET)
    public HttpEntity<ResourceSupport> getPropertyByid(@PathVariable("propertyId") final String propertyId) {

        Property property = this.propertyService.findPropertyByPrimaryKey(propertyId);

        return new ResponseEntity<ResourceSupport>(this.propertyResourceAssembler.toResource(property), HttpStatus.OK);
    }

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
    public HttpEntity<PagedResources<ResourceSupport>> getPropertiesByPage( //
            @RequestParam(value = "longitude", required = true) final Double longitude, //
            @RequestParam(value = "latitude", required = true) final Double latitude, //
            @RequestParam(value = "distance", required = false, defaultValue = "1.0") final Double distance, //
            @RequestParam(value = "sortOrder", required = false, defaultValue = "DESC") final String sortOrderAsString, //
            PagedResourcesAssembler<Property> assembler //
    ) {

        List<Property> properties = this.propertyService.findPropertiesByLocation(new GeoPoint(latitude, longitude), distance,
                SortOrder.valueOf(sortOrderAsString.toUpperCase()));

        return new ResponseEntity<PagedResources<ResourceSupport>>(assembler.toResource(new PageImpl<Property>(properties), this.propertyResourceAssembler),
                HttpStatus.OK);

    }

    /**
     * ----------------------------------------------------------------------------------------------------------------
     * 
     * GET - search "Pageable" properties by address (street, state, city).
     * 
     * ----------------------------------------------------------------------------------------------------------------
     * 
     * Return a pageable list of Properties.
     * 
     * @param ac
     *            Flag indicating if is an auto-complete search or not.
     * @param qt
     *            The query type. "none" (search all), "address" search by address. 
     * @param q
     *            The query string
     * @param pageable
     *            the page data. Page number and page size.
     * @param assembler
     *            the assembler that will construct the property resource as a pageable resource.
     * @return A pageable list of properties in json or xml format (default to json)
     * 
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public HttpEntity<PagedResources<ResourceSupport>> searchPropertiesByPage( //
            @RequestParam(value = "ac", required = false, defaultValue = "false") final Boolean autocomplete, //
            @RequestParam(value = "qt", required = false, defaultValue = "none") final String queryType, //
            @RequestParam(value = "q", required = false) final String queryValue, //
            @PageableDefault(size = 10, page = 0) Pageable pageable, //
            PagedResourcesAssembler<Property> assembler //
    ) {
        
        Page<Property> properties = null;

        if( queryType.trim().equals("none") ){
            properties = this.propertyService.findAll(pageable);
        } else if( queryType.trim().equals("address") ){
            properties = this.propertyService.searchPropertiesByAddress(queryValue, autocomplete, pageable);
        }

        return new ResponseEntity<PagedResources<ResourceSupport>>(assembler.toResource(properties, this.propertyResourceAssembler), HttpStatus.OK);

    }

}
