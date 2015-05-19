package edu.searchahouse.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.searchahouse.exceptions.PropertyNotFoundException;
import edu.searchahouse.model.Property;
import edu.searchahouse.model.repository.mongo.PropertyRepository;
import edu.searchahouse.service.PropertyService;

@Service
public class PropertyServiceImpl implements PropertyService {

	private final PropertyRepository propertyRepository;

	@Autowired
	public PropertyServiceImpl(PropertyRepository propertyRepository) {
		this.propertyRepository = propertyRepository;
	}

	@Override
	public Property findPropertyById(String id) {
		return this.propertyRepository.findPropertyById(id).orElseThrow(() -> new PropertyNotFoundException(id));
	}
}
