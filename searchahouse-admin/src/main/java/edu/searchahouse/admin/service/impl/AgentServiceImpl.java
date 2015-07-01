package edu.searchahouse.admin.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.searchahouse.admin.model.Agent;
import edu.searchahouse.admin.service.AgentService;

@Service
public class AgentServiceImpl implements AgentService {

	private final RestTemplate restTemplate;

	@Autowired
	public AgentServiceImpl(final RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public Page<Agent> findAllAgents(Pageable pageable) {

		String endpoint = "http://localhost:8080/api/v1/agent?page=" + pageable.getPageNumber() + "&size=" + pageable.getPageSize()
				+ (pageable.getSort() == null ? "" : "&sort=" + pageable.getSort());

		ResponseEntity<PagedResources<Resource<Agent>>> pagedResourceResponse = this.restTemplate.exchange(endpoint, HttpMethod.GET, null,
				new ParameterizedTypeReference<PagedResources<Resource<Agent>>>() {
				});

		List<Agent> agentList = pagedResourceResponse.getBody().getContent().stream().map(Resource::getContent).collect(Collectors.toList());

		Page<Agent> agents = new PageImpl<Agent>(agentList, pageable, pagedResourceResponse.getBody().getMetadata().getTotalElements());

		return agents;
	}

	@Override
	public void deleteAgent(String agentId) {

		final String endpoint = "http://localhost:8080/api/v1/agent/" + agentId;

		this.restTemplate.delete(endpoint);

	}

}
