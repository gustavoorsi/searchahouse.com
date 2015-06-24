package edu.searchahouse.searchengine.endpoints;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.searchahouse.searchengine.endpoints.hal.resources.LocationResource;
import edu.searchahouse.searchengine.endpoints.hal.resources.assemblers.LocationResourceAssembler;
import edu.searchahouse.searchengine.model.Location;
import edu.searchahouse.searchengine.service.LocationService;

@RestController
@RequestMapping("/api/v1/location")
public class LocationRestEndpoint {

    // *************************************************************//
    // *********************** PROPERTIES **************************//
    // *************************************************************//
    private final LocationService locationService;
    private final LocationResourceAssembler locationResourceAssembler;

    // *************************************************************//
    // *********************** CONSTRUCTORS ************************//
    // *************************************************************//
    //@formatter:off
	@Autowired
	public LocationRestEndpoint(
	        final LocationService locationService, 
	        final LocationResourceAssembler locationResourceAssembler
        ) {
		this.locationService = locationService;
		this.locationResourceAssembler = locationResourceAssembler;
	}
	//@formatter:on

    // *************************************************************//
    // ********************* REST ENDPOINTS ************************//
    // *************************************************************//

    /**
     * ----------------------------------------------------------------------------------------------------------------
     * 
     * GET - find "Pageable" locations by state
     * 
     * ----------------------------------------------------------------------------------------------------------------
     * 
     * Return a pageable list of locations.
     * 
     * @param pageable
     *            the page data. Page number and page size.
     * @param assembler
     *            the assembler that will construct the agent resource as a pageable resource.
     * @return A pageable list of properties in json or xml format (default to json)
     * 
     */
    @RequestMapping(value = "/autocomplete/{state}", method = RequestMethod.GET)
    public HttpEntity<PagedResources<LocationResource>> getLocationsByState( //
            @PathVariable String state, //
            @PageableDefault(size = 10, page = 0) Pageable pageable, //
            PagedResourcesAssembler<Location> assembler //
    ) {

        List<Location> locations = this.locationService.findLocationsByState(state);

        return new ResponseEntity<PagedResources<LocationResource>>(assembler.toResource(new PageImpl<Location>(locations), this.locationResourceAssembler),
                HttpStatus.OK);

    }
}
