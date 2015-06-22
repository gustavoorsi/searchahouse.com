package edu.searchahouse.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.searchahouse.web.model.Property;
import edu.searchahouse.web.service.PropertyService;

@Service
public class PropertyServiceImpl implements PropertyService {

	private final RestTemplate restTemplate;

	@Autowired
	public PropertyServiceImpl(final RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public Property findById(String id) {

		String endpoint = "http://localhost:8080/api/v1/property/" + id;

		ResponseEntity<Resource<Property>> resourceResponse = this.restTemplate.exchange(endpoint, HttpMethod.GET, null,
				new ParameterizedTypeReference<Resource<Property>>() {
				});

		Property property = resourceResponse.getBody().getContent();

		return property;
	}

}
