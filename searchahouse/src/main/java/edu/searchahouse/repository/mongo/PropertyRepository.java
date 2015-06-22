package edu.searchahouse.repository.mongo;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import edu.searchahouse.model.Property;

public interface PropertyRepository extends MongoRepository<Property, ObjectId> {

	Optional<Property> findPropertyById(final ObjectId id);
	
	Optional<Property> findPropertyByName(final String name);

}
