package edu.searchahouse.repository.mongo;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import edu.searchahouse.model.Agent;

public interface AgentRepository extends MongoRepository<Agent, ObjectId> {

	Optional<Agent> findAgentByPrimaryKey(final ObjectId primaryKey);
    
    @Query(value="{ '_id' : ?0 }", fields="{ 'properties' : 0, 'leads' : 0}")
    Optional<Agent> findAgentByPrimaryKeyLazyNestedCollections(final ObjectId id);
    
    @Query(value="{ 'properties._id' : ?0 }", fields="{ 'properties' : 0, 'leads' : 0}")
	Page<Agent> findAgentsByProperty(final ObjectId propertyPrimaryKey, Pageable pageable);
    
    @Query(value="{ 'properties._id' : ?0 }")
    Page<Agent> findAgentsByPropertyIncludeNestedCollections(final ObjectId propertyPrimaryKey, Pageable pageable);

	Optional<Agent> findAgentByEmail(final String email);

}
