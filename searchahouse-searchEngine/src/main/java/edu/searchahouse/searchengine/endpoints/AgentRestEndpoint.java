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

import edu.searchahouse.searchengine.endpoints.hal.resources.AgentResource;
import edu.searchahouse.searchengine.endpoints.hal.resources.assemblers.AgentResourceAssembler;
import edu.searchahouse.searchengine.model.Agent;
import edu.searchahouse.searchengine.service.AgentService;

@RestController
@RequestMapping("/api/v1/agent")
public class AgentRestEndpoint {

	// *************************************************************//
	// *********************** PROPERTIES **************************//
	// *************************************************************//
	private final AgentService agentService;

	private final AgentResourceAssembler agentResourceAssembler;

	// *************************************************************//
	// *********************** CONSTRUCTORS ************************//
	// *************************************************************//
	@Autowired
	public AgentRestEndpoint(final AgentService agentService, final AgentResourceAssembler agentResourceAssembler) {
		this.agentService = agentService;
		this.agentResourceAssembler = agentResourceAssembler;
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
	@RequestMapping(value = "/autocomplete/{firstName}", method = RequestMethod.GET)
	public HttpEntity<PagedResources<AgentResource>> getAgentsByFirstName( //
			@PathVariable String firstName, //
			@PageableDefault(size = 10, page = 0) Pageable pageable, //
			PagedResourcesAssembler<Agent> assembler //
	) {

		List<Agent> agents = this.agentService.findAgentsByFirstName(firstName);

		return new ResponseEntity<PagedResources<AgentResource>>(assembler.toResource(new PageImpl<Agent>(agents), this.agentResourceAssembler), HttpStatus.OK);

	}

}
