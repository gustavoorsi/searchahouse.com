package edu.searchahouse.searchengine.endpoints.hal.resources;

import org.springframework.hateoas.Resource;

import edu.searchahouse.searchengine.model.Agent;

public class AgentResource extends Resource<Agent> {

	public AgentResource(final Agent content) {
		super(content);
	}

}
