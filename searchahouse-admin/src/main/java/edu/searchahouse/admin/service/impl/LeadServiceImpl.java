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

import edu.searchahouse.admin.model.Lead;
import edu.searchahouse.admin.service.LeadsService;

@Service
public class LeadServiceImpl implements LeadsService {

	private final RestTemplate restTemplate;

	@Autowired
	public LeadServiceImpl(final RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public Page<Lead> findAllLeads(Pageable pageable) {
		String endpoint = "http://localhost:8080/api/v1/lead?page=" + pageable.getPageNumber() + "&size=" + pageable.getPageSize()
				+ (pageable.getSort() == null ? "" : "&sort=" + pageable.getSort());

		ResponseEntity<PagedResources<Resource<Lead>>> pagedResourceResponse = this.restTemplate.exchange(endpoint, HttpMethod.GET, null,
				new ParameterizedTypeReference<PagedResources<Resource<Lead>>>() {
				});

		List<Lead> leadList = pagedResourceResponse.getBody().getContent().stream().map(Resource::getContent).collect(Collectors.toList());

		Page<Lead> leads = new PageImpl<Lead>(leadList, pageable, pagedResourceResponse.getBody().getMetadata().getTotalElements());

		return leads;
	}

	@Override
	public void deleteLead(String leadId) {
		final String endpoint = "http://localhost:8080/api/v1/lead/" + leadId;

		this.restTemplate.delete(endpoint);

	}

}
