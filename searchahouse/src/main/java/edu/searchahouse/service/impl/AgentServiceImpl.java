package edu.searchahouse.service.impl;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mongodb.WriteResult;

import edu.searchahouse.exceptions.EntityNotFoundException;
import edu.searchahouse.exceptions.EntityNotUpdatedException;
import edu.searchahouse.model.Agent;
import edu.searchahouse.model.Lead;
import edu.searchahouse.model.Property;
import edu.searchahouse.repository.mongo.AgentRepository;
import edu.searchahouse.repository.mongo.LeadRepository;
import edu.searchahouse.repository.mongo.PropertyRepository;
import edu.searchahouse.service.AgentService;

@Service
public class AgentServiceImpl extends BaseService implements AgentService {

    private final AgentRepository agentRepository;
    private final PropertyRepository propertyRepository;
    private final LeadRepository leadRepository;

    @Autowired
    public AgentServiceImpl(//
            final AgentRepository agentRepository, //
            final PropertyRepository propertyRepository, //
            final MongoOperations mongoOperations, //
            final LeadRepository leadRepository //
    ) {
        super(mongoOperations);
        this.agentRepository = agentRepository;
        this.propertyRepository = propertyRepository;
        this.leadRepository = leadRepository;
    }

    @Override
    public Agent findAgentByPrimaryKey(String id, final boolean lazyNested) {

        Optional<Agent> agent;

        if (lazyNested) {
            agent = this.agentRepository.findAgentByPrimaryKeyLazyNestedCollections(new ObjectId(id));
        } else {
            agent = this.agentRepository.findAgentByPrimaryKey(new ObjectId(id));
        }

        return agent.orElseThrow(() -> new EntityNotFoundException("Agent"));

    }

    @Override
    public Page<Agent> findAgentsByPropertyId(String propertyId, Pageable pageable) {
        return this.agentRepository.findAgentsByProperty(new ObjectId(propertyId), pageable);
    }

    @Override
    public Page<Agent> getAgentsByPage(Pageable pageable) {
        return this.agentRepository.findAll(pageable);
    }

    @Override
    public Agent save(Agent input) {
        return this.agentRepository.save(input);
    }

    @Override
    public Agent update(String agentId, Agent input) {
        return (Agent) super.update(agentId, input);
    }

    @Override
    public Agent addLead(final String agentId, Lead lead) {

        Agent agent = this.agentRepository.findAgentByPrimaryKey(new ObjectId(agentId)).orElseThrow(() -> new EntityNotFoundException("Agent"));

        this.leadRepository.save(lead);
        agent.addLead(lead);

        return this.update(agentId, agent);
    }

    @Override
    public Property addProperty(final String agentId, final String propertyId) {

        Agent agent = this.agentRepository.findAgentByPrimaryKey(new ObjectId(agentId)).orElseThrow(() -> new EntityNotFoundException("Agent"));
        Property property = this.propertyRepository.findPropertyByPrimaryKey(new ObjectId(propertyId)).orElseThrow(
                () -> new EntityNotFoundException("Property"));

        agent.addProperty(property);

        this.agentRepository.save(agent);

        return property;
    }

    @Override
    public void updateLeadContactStatus(String agentId, String leadId, Lead lead) {

        // update the lead entity
        Query query = new Query(Criteria.where("_id").is(new ObjectId(leadId)) );
        Update update = new Update();
        update.set("contactStatus", lead.getContactStatus());
        
        WriteResult writeResult = getMongoOperations().updateMulti(query, update, Lead.class);
        
        if (writeResult.getN() == 0) {
            throw new EntityNotUpdatedException("Lead");
        }
        
        // update the nested lead entity in agent
        Query queryNested = new Query(Criteria.where("_id").is(new ObjectId(agentId)).and("leads._id").is(new ObjectId(leadId)));
        
        Update updateNested = new Update();
        updateNested.set("leads.0.contactStatus", lead.getContactStatus());
        
        WriteResult writeResultNested = getMongoOperations().updateMulti(queryNested, updateNested, Agent.class);
        
        if (writeResultNested.getN() == 0) {
            throw new EntityNotUpdatedException("Nested Lead");
        }

    }

}
