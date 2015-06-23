package edu.searchahouse.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.searchahouse.web.model.Agent;
import edu.searchahouse.web.service.AgentService;

@Controller
@RequestMapping(value = "/agents")
public class AgentController {

    private final AgentService agentService;

    @Autowired
    public AgentController(final AgentService agentService) {
        this.agentService = agentService;
    }
    
    @RequestMapping(value = "/{agentId}", method = RequestMethod.GET)
    public ModelAndView getAgent(final @PathVariable("agentId") String agentId) {

        Map<String, Object> model = new HashMap<String, Object>();

        Agent agent = this.agentService.findById(agentId);

        model.put("agent", agent);

        return new ModelAndView("sections/agent/agent", model);
    }

}
