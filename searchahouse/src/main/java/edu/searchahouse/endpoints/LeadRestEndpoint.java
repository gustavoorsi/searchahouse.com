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

import edu.searchahouse.endpoints.resources.LeadResource;
import edu.searchahouse.endpoints.resources.assemblers.LeadResourceAssembler;
import edu.searchahouse.model.Lead;
import edu.searchahouse.service.LeadService;

@RestController
@RequestMapping("/api/v1/lead")
public class LeadRestEndpoint {

	// *************************************************************//
	// *********************** PROPERTIES **************************//
	// *************************************************************//
	private final LeadService leadService;

	private final LeadResourceAssembler leadResourceAssembler;

	// *************************************************************//
	// *********************** CONSTRUCTORS ************************//
	// *************************************************************//
	@Autowired
	public LeadRestEndpoint(LeadService leadService, LeadResourceAssembler leadResourceAssembler) {
		this.leadService = leadService;
		this.leadResourceAssembler = leadResourceAssembler;
	}

	// *************************************************************//
	// ********************* REST ENDPOINTS ************************//
	// *************************************************************//

	/**
	 * ----------------------------------------------------------------------------------------------------------------
	 * 
	 * GET - find "Pageable" leads
	 * 
	 * ----------------------------------------------------------------------------------------------------------------
	 * 
	 * Return a pageable list of Leads.
	 * 
	 * @param pageable
	 *            the page data. Page number and page size.
	 * @param assembler
	 *            the assembler that will construct the lead resource as a pageable resource.
	 * @return A pageable list of leads in json or xml format (default to json)
	 * 
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public HttpEntity<PagedResources<LeadResource>> getLeadsByPage( //
			@PageableDefault(size = 10, page = 0) Pageable pageable, //
			PagedResourcesAssembler<Lead> assembler //
	) {

		Page<Lead> leads = this.leadService.getLeadsByPage(pageable);

		return new ResponseEntity<PagedResources<LeadResource>>(assembler.toResource(leads, this.leadResourceAssembler), HttpStatus.OK);

	}

	/**
	 *
	 * ----------------------------------------------------------------------------------------------------------------
	 * 
	 * GET - find a Lead by id
	 * 
	 * ----------------------------------------------------------------------------------------------------------------
	 * 
	 * Get a lead by it's primary key. Throw 404 if not found.
	 * 
	 * @param leadId
	 * @return A lead in json or xml format (default to json).
	 * 
	 */
	@RequestMapping(value = "/{leadId}", method = RequestMethod.GET)
	public HttpEntity<LeadResource> getLead(@PathVariable String leadId) {
		Lead aLead = this.leadService.findLeadById(leadId);

		return new ResponseEntity<LeadResource>(this.leadResourceAssembler.toResource(aLead), HttpStatus.OK);
	}

	/**
	 * ----------------------------------------------------------------------------------------------------------------
	 * 
	 * POST - Create a lead
	 * 
	 * ----------------------------------------------------------------------------------------------------------------
	 * 
	 * Create a new lead. Throw 422 if resource already exist.
	 * 
	 * @param leadId
	 * @return 201 Created and the lead location. 422 if the lead already exist.
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public HttpEntity<?> createLead(@RequestBody Lead input) {

		Lead lead = this.leadService.save(input);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(linkTo(methodOn(LeadRestEndpoint.class, lead.getId()).getLead(lead.getId())).toUri());

		return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
	}

	/**
	 * ----------------------------------------------------------------------------------------------------------------
	 * 
	 * PUT - Update a lead
	 * 
	 * ----------------------------------------------------------------------------------------------------------------
	 * 
	 * Updates an existing lead. Throw 402 if resource does not exist or 403 in case the resource exist but could not be updated.
	 * 
	 * @param leadId
	 * @return 204 if updated ok, 402 if resource does not exist or 403 in case the resource exist but could not be updated.
	 */
	@RequestMapping(value = "/{leadId}", method = RequestMethod.PUT)
	public HttpEntity<?> updateLead( //
			@RequestBody Lead input,//
			@PathVariable String leadId //
	) {

		Lead lead = this.leadService.update(leadId, input);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(linkTo(methodOn(LeadRestEndpoint.class, lead.getId()).getLead(lead.getId())).toUri());

		return new ResponseEntity<>("The resource was updated ok.", httpHeaders, HttpStatus.NO_CONTENT);
	}

}
