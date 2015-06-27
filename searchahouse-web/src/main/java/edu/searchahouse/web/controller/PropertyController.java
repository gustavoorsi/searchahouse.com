package edu.searchahouse.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.searchahouse.web.model.Lead;
import edu.searchahouse.web.model.Property;
import edu.searchahouse.web.service.PropertyService;

@Controller
@RequestMapping(value = "/properties")
public class PropertyController {

	private final PropertyService propertyService;

	@Autowired
	public PropertyController(final PropertyService propertyService) {
		this.propertyService = propertyService;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView getAgent(final @PathVariable("id") String id) {

		Map<String, Object> model = new HashMap<String, Object>();

		Property property = this.propertyService.findById(id);

		model.put("property", property);
		model.put("lead", new Lead());

		return new ModelAndView("sections/property/property", model);
	}

}
