package edu.searchahouse.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.searchahouse.web.model.Lead;

@Controller
@RequestMapping(value = "/leads")
public class LeadController {

	public LeadController() {
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView saveNewLead(Lead lead) {
		return new ModelAndView("sections/property/property");
	}

}
