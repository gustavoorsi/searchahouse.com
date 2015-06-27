package edu.searchahouse.repository.mongo;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import edu.searchahouse.model.Lead;

public interface LeadRepository extends MongoRepository<Lead, ObjectId> {

	Optional<Lead> findLeadByPrimaryKey(final ObjectId primaryKey);

	Optional<Lead> findLeadByEmail(final String email);

}
