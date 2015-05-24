package edu.searchahouse.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import edu.searchahouse.exceptions.EntityNotFoundException;
import edu.searchahouse.model.Property;
import edu.searchahouse.model.repository.mongo.PropertyRepository;
import edu.searchahouse.service.PropertyService;

@Service
public class PropertyServiceImpl extends BaseService implements PropertyService {

	private final PropertyRepository propertyRepository;

	@Autowired
	public PropertyServiceImpl(//
			final PropertyRepository propertyRepository, //
			final MongoOperations mongoOperations//
	) {
		super(mongoOperations);
		this.propertyRepository = propertyRepository;
	}

	@Override
	public Property findPropertyById(String id) {
		return this.propertyRepository.findPropertyById(id).orElseThrow(() -> new EntityNotFoundException("Property"));
	}

	@Override
	public Page<Property> getPropertiesByPage(Pageable pageable) {
		return this.propertyRepository.findAll(pageable);
	}

	@Override
	public Property save(Property input) {
		return this.propertyRepository.save(input);
	}

	@Override
	public Property update(String propertyId, Property input) {
		return (Property) super.update(propertyId, input);
	}

}
