package edu.searchahouse.searchengine.endpoints;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RestController;

import edu.searchahouse.searchengine.endpoints.hal.resources.assemblers.AgentResourceAssembler;
import edu.searchahouse.searchengine.endpoints.hal.resources.assemblers.PropertyResourceAssembler;
import edu.searchahouse.searchengine.model.Agent;
import edu.searchahouse.searchengine.model.Property;
import edu.searchahouse.searchengine.service.AgentService;
import edu.searchahouse.searchengine.service.PropertyService;

@RestController
@RequestMapping("/api/v1/agent")
public class AgentRestEndpoint {

    // *************************************************************//
    // *********************** PROPERTIES **************************//
    // *************************************************************//
    private final AgentService agentService;
    private final PropertyService propertyService;
    private final AgentResourceAssembler agentResourceAssembler;
    private final PropertyResourceAssembler propertyResourceAssembler;

    // *************************************************************//
    // *********************** CONSTRUCTORS ************************//
    // *************************************************************//
    //@formatter:off
	@Autowired
	public AgentRestEndpoint(
	        final AgentService agentService, 
	        final PropertyService propertyService, 
	        final AgentResourceAssembler agentResourceAssembler,
	        final PropertyResourceAssembler propertyResourceAssembler
        ) {
		this.agentService = agentService;
		this.propertyService = propertyService;
		this.agentResourceAssembler = agentResourceAssembler;
		this.propertyResourceAssembler = propertyResourceAssembler;
	}
	//@formatter:on

    // *************************************************************//
    // ********************* REST ENDPOINTS ************************//
    // *************************************************************//
    /**
     * ----------------------------------------------------------------------------------------------------------------
     * 
     * GET - find "Pageable" agents.
     * 
     * ----------------------------------------------------------------------------------------------------------------
     * 
     * Return a pageable list of Agents.
     * 
     * @param pageable
     *            the page data. Page number and page size.
     * @param assembler
     *            the assembler that will construct the agent resource as a pageable resource.
     * @return A pageable list of properties in json or xml format (default to json)
     * 
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public HttpEntity<PagedResources<ResourceSupport>> getAgents(//
            @PageableDefault(size = 10, page = 0) Pageable pageable, //
            PagedResourcesAssembler<Agent> assembler //
    ) {

        Page<Agent> agents = this.agentService.findAll(pageable);

        return new ResponseEntity<>(assembler.toResource(agents, this.agentResourceAssembler), HttpStatus.OK);
    }

    /**
     * ----------------------------------------------------------------------------------------------------------------
     * 
     * GET - find "agent by id
     * 
     * ----------------------------------------------------------------------------------------------------------------
     * 
     * Return an agent.
     * 
     * @param id
     *            the id to search for.
     * @return An agent in json or xml format (default to json)
     * 
     */
    @RequestMapping(value = "/{agentId}", method = RequestMethod.GET)
    public HttpEntity<ResourceSupport> getAgent(@PathVariable("agentId") final String agentId) {

        Agent agent = this.agentService.findAgentByPrimaryKey(agentId);

        return new ResponseEntity<ResourceSupport>(this.agentResourceAssembler.toResource(agent), HttpStatus.OK);

    }

    /**
     * ----------------------------------------------------------------------------------------------------------------
     * 
     * GET - find "Pageable" agents by first name
     * 
     * ----------------------------------------------------------------------------------------------------------------
     * 
     * Return a pageable list of Agents.
     * 
     * @param pageable
     *            the page data. Page number and page size.
     * @param assembler
     *            the assembler that will construct the agent resource as a pageable resource.
     * @return A pageable list of properties in json or xml format (default to json)
     * 
     */
    @RequestMapping(value = "/autocomplete/{firstName}", method = RequestMethod.GET)
    public HttpEntity<PagedResources<ResourceSupport>> getAgentsByFirstName( //
            @PathVariable String firstName, //
            @PageableDefault(size = 10, page = 0) Pageable pageable, //
            PagedResourcesAssembler<Agent> assembler //
    ) {

        List<Agent> agents = this.agentService.findAgentsByFirstName(firstName);

        return new ResponseEntity<PagedResources<ResourceSupport>>(assembler.toResource(new PageImpl<Agent>(agents), this.agentResourceAssembler),
                HttpStatus.OK);

    }

    /**
     * ----------------------------------------------------------------------------------------------------------------
     * 
     * GET - find "Pageable" properties of the agent.
     * 
     * ----------------------------------------------------------------------------------------------------------------
     * 
     * Return a pageable list of properties of the agent.
     * 
     * @param pageable
     *            the page data. Page number and page size.
     * @param assembler
     *            the assembler that will construct the property resource as a pageable resource.
     * @return A pageable list of properties in json or xml format (default to json)
     * 
     */
    @RequestMapping(value = "/{agentId}/properties", method = RequestMethod.GET)
    public HttpEntity<PagedResources<ResourceSupport>> getAgentProperties( //
            @PathVariable String agentId, //
            @PageableDefault(size = 10, page = 0) Pageable pageable, //
            PagedResourcesAssembler<Property> assembler //
    ) {

        List<Property> properties = this.propertyService.findPropertiesByAgentId(agentId);

        return new ResponseEntity<PagedResources<ResourceSupport>>(assembler.toResource(new PageImpl<Property>(properties), this.propertyResourceAssembler),
                HttpStatus.OK);

    }

}
