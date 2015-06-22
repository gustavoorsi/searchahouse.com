package edu.searchahouse.repository.mongo;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import edu.searchahouse.model.Agent;

public interface AgentRepository extends MongoRepository<Agent, ObjectId> {

	Optional<Agent> findAgentById(final ObjectId id);

	Optional<Agent> findAgentByEmail(final String email);

}
