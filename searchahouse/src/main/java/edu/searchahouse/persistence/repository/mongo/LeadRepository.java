package edu.searchahouse.persistence.repository.mongo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.searchahouse.model.Lead;

public interface LeadRepository extends MongoRepository<Lead, String> {

	Optional<Lead> findLeadById(final String id);

	Optional<Lead> findLeadByEmail(final String email);

}
