package edu.searchahouse.web.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.searchahouse.web.model.Agent;
import edu.searchahouse.web.service.AgentService;


@Controller
@RequestMapping(value = "/")
public class HomeController {
    
    private final AgentService agentService;
    
    @Autowired
    public HomeController( final AgentService agentService ) {
        this.agentService = agentService;
    }

    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getHome() {
        
        Map<String, Object> model = new HashMap<String, Object>();
        
        Collection<Agent> agents = this.agentService.findTopAgents( 3 );
        
        model.put("agents", agents);
        
        return new ModelAndView("sections/home/home", model);
    }
    
}
