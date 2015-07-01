package edu.searchahouse.admin.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.searchahouse.admin.model.Agent;
import edu.searchahouse.admin.model.Property;
import edu.searchahouse.admin.service.AgentService;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

	private final AgentService agentService;

	@Autowired
	public AdminController(final AgentService agentService) {
		this.agentService = agentService;
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

		return listAgents(null);
	}

	@RequestMapping(value = "/listAllProperties", method = RequestMethod.GET)
	public ModelAndView listProperties(@PageableDefault(size = 10, page = 0) final Pageable pageable) {

		Map<String, Object> model = new HashMap<String, Object>();

		Page<Property> properties = new PageImpl<Property>(Arrays.asList(new Property(), new Property()));

		model.put("properties", properties);
		model.put("page", "properties");

		return new ModelAndView("sections/property/properties", model);
	}

}
