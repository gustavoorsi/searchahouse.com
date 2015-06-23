package edu.searchahouse.repository.mongo;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import edu.searchahouse.model.Agent;

public interface AgentRepository extends MongoRepository<Agent, ObjectId> {

    @Query(value="{ 'id' : ?0 }", fields="{ 'properties' : 0, 'leads' : 0}")
	Optional<Agent> findAgentById(final ObjectId id);

	Optional<Agent> findAgentByEmail(final String email);

}
