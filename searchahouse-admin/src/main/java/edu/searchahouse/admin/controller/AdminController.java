package edu.searchahouse.admin.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.searchahouse.admin.model.Agent;
import edu.searchahouse.admin.model.Lead;
import edu.searchahouse.admin.model.Property;
import edu.searchahouse.admin.service.AgentService;
import edu.searchahouse.admin.service.LeadsService;
import edu.searchahouse.admin.service.PropertyService;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

	private final AgentService agentService;
	private final PropertyService propertyService;
	private final LeadsService leadService;

	@Autowired
	public AdminController(final AgentService agentService, final PropertyService propertyService, final LeadsService leadService) {
		this.agentService = agentService;
		this.propertyService = propertyService;
		this.leadService = leadService;
	}

	@RequestMapping(value = "/listAllAgents", method = RequestMethod.GET)
	public ModelAndView listAgents(@PageableDefault(size = 10, page = 0) final Pageable pageable) {

		Map<String, Object> model = new HashMap<String, Object>();

		Page<Agent> agents = this.agentService.findAllAgents(pageable);

		model.put("agents", agents);
		model.put("page", "agents");

		return new ModelAndView("sections/agent/agents", model);
	}

	@RequestMapping(value = "/deleteAgent", method = RequestMethod.GET)
	public ModelAndView deleteAgent(@RequestParam("agentId") final String agentId) {

		this.agentService.deleteAgent(agentId);

		return listAgents(new PageRequest(0, 10));
	}

	@RequestMapping(value = "/listAllProperties", method = RequestMethod.GET)
	public ModelAndView listProperties(@PageableDefault(size = 10, page = 0) final Pageable pageable) {

		Map<String, Object> model = new HashMap<String, Object>();

		Page<Property> properties = this.propertyService.findAllProperties(pageable);

		model.put("properties", properties);
		model.put("page", "properties");

		return new ModelAndView("sections/property/properties", model);
	}

	@RequestMapping(value = "/deleteProperty", method = RequestMethod.GET)
	public ModelAndView deleteProperty(@RequestParam("propertyId") final String propertyId) {

		this.propertyService.deleteProperty(propertyId);

		return listProperties(new PageRequest(0, 10));
	}
	
	@RequestMapping(value = "/listAllLeads", method = RequestMethod.GET)
	public ModelAndView listLeads(@PageableDefault(size = 10, page = 0) final Pageable pageable) {

		Map<String, Object> model = new HashMap<String, Object>();

		Page<Lead> leads = this.leadService.findAllLeads(pageable);

		model.put("leads", leads);
		model.put("page", "leads");

		return new ModelAndView("sections/lead/leads", model);
	}

}
