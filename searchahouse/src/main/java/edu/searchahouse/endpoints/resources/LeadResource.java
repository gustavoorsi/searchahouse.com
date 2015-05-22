package edu.searchahouse.endpoints.resources;

import org.springframework.hateoas.Resource;

import edu.searchahouse.model.Lead;

public class LeadResource extends Resource<Lead> {

	public LeadResource(final Lead content) {
		super(content);
	}

}
