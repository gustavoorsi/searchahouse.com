package edu.searchahouse.persistence.repository.mongo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.searchahouse.model.Agent;

public interface AgentRepository extends MongoRepository<Agent, String> {

	Optional<Agent> findAgentById(final String id);

	Optional<Agent> findAgentByEmail(final String email);

}
