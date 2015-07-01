package edu.searchahouse.repository.mongo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.searchahouse.model.Property;

public interface PropertyRepository extends MongoRepository<Property, String> {

	Optional<Property> findPropertyByPrimaryKey(final String primaryKey);

	Optional<Property> findPropertyByName(final String name);

}
