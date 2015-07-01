package edu.searchahouse.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.searchahouse.admin.model.Property;

public interface PropertyService {

	Page<Property> findAllProperties(final Pageable pageable);

	void deleteProperty(final String propertyId);

}
