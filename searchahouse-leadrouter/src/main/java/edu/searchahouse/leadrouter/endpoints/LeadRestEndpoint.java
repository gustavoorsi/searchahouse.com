package edu.searchahouse.leadrouter.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.searchahouse.leadrouter.model.Lead;
import edu.searchahouse.leadrouter.service.LeadRouterService;

@RestController
@RequestMapping("/api/v1/lead")
public class LeadRestEndpoint {

    // *************************************************************//
    // *********************** PROPERTIES **************************//
    // *************************************************************//
    private final LeadRouterService leadRouterService;

    // *************************************************************//
    // *********************** CONSTRUCTORS ************************//
    // *************************************************************//
    @Autowired
    public LeadRestEndpoint(final LeadRouterService leadRouterService) {
        this.leadRouterService = leadRouterService;
    }

    // *************************************************************//
    // ********************* REST ENDPOINTS ************************//
    // *************************************************************//

    /**
     * ----------------------------------------------------------------------------------------------------------------
     * 
     * POST - Route a lead to an agent. That means, decide which agent should receive this new lead.
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
    @RequestMapping(value = "", method = RequestMethod.POST)
    public HttpEntity<?> routeLead( //
            @RequestBody Lead lead, //
            @RequestParam(value = "propertyId", required = true) final String propertyId //
    ) {
        
        this.leadRouterService.routeLead(lead, propertyId);
       

        HttpHeaders httpHeaders = new HttpHeaders();
        // TODO: point to the API microservice, to the lead endpoint.
        // httpHeaders.setLocation();

        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

}
