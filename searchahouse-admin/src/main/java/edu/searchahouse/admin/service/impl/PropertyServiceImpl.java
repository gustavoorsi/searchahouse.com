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

import edu.searchahouse.admin.model.Property;
import edu.searchahouse.admin.service.PropertyService;

@Service
public class PropertyServiceImpl implements PropertyService {

	private final RestTemplate restTemplate;

	@Autowired
	public PropertyServiceImpl(final RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public Page<Property> findAllProperties(Pageable pageable) {
		String endpoint = "http://localhost:8080/api/v1/property?page=" + pageable.getPageNumber() + "&size=" + pageable.getPageSize()
				+ (pageable.getSort() == null ? "" : "&sort=" + pageable.getSort());

		ResponseEntity<PagedResources<Resource<Property>>> pagedResourceResponse = this.restTemplate.exchange(endpoint, HttpMethod.GET, null,
				new ParameterizedTypeReference<PagedResources<Resource<Property>>>() {
				});

		List<Property> propertyList = pagedResourceResponse.getBody().getContent().stream().map(Resource::getContent).collect(Collectors.toList());

		Page<Property> properties = new PageImpl<Property>(propertyList, pageable, pagedResourceResponse.getBody().getMetadata().getTotalElements());

		return properties;
	}

	@Override
	public void deleteProperty(String propertyId) {
		final String endpoint = "http://localhost:8080/api/v1/property/" + propertyId;

		this.restTemplate.delete(endpoint);

	}

}
