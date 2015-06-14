package edu.searchahouse.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value = "/property")
public class PropertyController {

    
    @RequestMapping( value = "listAll", method = RequestMethod.GET)
    public ModelAndView getCompanies() {

        return new ModelAndView("property/listAll");

    }
    
}
