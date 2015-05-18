
package edu.searchahouse.model.repository.mongo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.searchahouse.model.Property;

public interface PropertyRepository extends MongoRepository<Property, String> {

	Optional<Property> findPropertyById(String id);

}
