package edu.searchahouse.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import edu.searchahouse.exceptions.PropertyNotFoundException;
import edu.searchahouse.model.BaseEntity;
import edu.searchahouse.model.Property;
import edu.searchahouse.model.repository.mongo.PropertyRepository;
import edu.searchahouse.service.PropertyService;

@Service
public class PropertyServiceImpl implements PropertyService {

	private final PropertyRepository propertyRepository;

	private final MongoTemplate mongoTemplate;

	@Autowired
	public PropertyServiceImpl(//
			final PropertyRepository propertyRepository, //
			final MongoTemplate mongoTemplate//
	) {
		this.propertyRepository = propertyRepository;
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public Property findPropertyById(String id) {
		return this.propertyRepository.findPropertyById(id).orElseThrow(() -> new PropertyNotFoundException(id));
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
	public Property update(String propertyId, Property inputProperty) {
		
		Query query = new Query(Criteria.where("_id").is(propertyId));
		Update update = createUpdate(inputProperty);
		
		this.mongoTemplate.upsert(query, update, Property.class);
		
		inputProperty.setId( propertyId );

		return inputProperty;
	}
	
	private Update createUpdate( final BaseEntity entity ){
		Update update = new Update();
		
		entity.toMap().forEach( (k,v) -> update.set(k, v) );
		
		return update;
	}

}
