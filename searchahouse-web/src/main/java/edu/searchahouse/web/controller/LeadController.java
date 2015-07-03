package edu.searchahouse.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.searchahouse.web.model.Lead;
import edu.searchahouse.web.service.AgentService;

@Controller
@RequestMapping(value = "/leads")
public class LeadController {
	
	private final AgentService agentService;

	@Autowired
	public LeadController( final AgentService agentService ) {
		this.agentService = agentService;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveNewLead(Lead lead, @RequestParam( value="propertyId", required=true) String propertyId) {
		
		this.agentService.addLeadToAgentForProperty(lead, propertyId);
		
		return "redirect:/properties/" + propertyId;
	}

}
