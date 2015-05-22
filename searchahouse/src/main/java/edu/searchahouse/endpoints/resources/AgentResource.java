package edu.searchahouse.endpoints.resources;

import org.springframework.hateoas.Resource;

import edu.searchahouse.model.Agent;

public class AgentResource extends Resource<Agent> {

	public AgentResource(final Agent content) {
		super(content);
	}

}
