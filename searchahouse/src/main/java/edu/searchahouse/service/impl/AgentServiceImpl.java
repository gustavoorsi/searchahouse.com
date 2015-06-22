package edu.searchahouse.service.impl;

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
import edu.searchahouse.model.LeadPortfolio;
import edu.searchahouse.model.Property;
import edu.searchahouse.repository.mongo.AgentRepository;
import edu.searchahouse.repository.mongo.PropertyRepository;
import edu.searchahouse.service.AgentService;

@Service
public class AgentServiceImpl extends BaseService implements AgentService {

	private final AgentRepository agentRepository;
	private final PropertyRepository propertyRepository;

	@Autowired
	public AgentServiceImpl(//
			final AgentRepository agentRepository, //
			final PropertyRepository propertyRepository, //
			final MongoOperations mongoOperations//
	) {
		super(mongoOperations);
		this.agentRepository = agentRepository;
		this.propertyRepository = propertyRepository;
	}

	@Override
	public Agent findAgentById(String id) {
		return this.agentRepository.findAgentById(new ObjectId(id)).orElseThrow(() -> new EntityNotFoundException("Agent"));
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
	public Property addProperty(final String agentId, final String propertyId) {

		Agent agent = this.agentRepository.findAgentById(new ObjectId(agentId)).orElseThrow(() -> new EntityNotFoundException("Agent"));
		Property property = this.propertyRepository.findPropertyById(new ObjectId(propertyId)).orElseThrow(() -> new EntityNotFoundException("Property"));

		agent.addProperty(property);

		this.agentRepository.save(agent);

		return property;
	}

	@Override
	public void updateLeadContactStatus(String agentId, String leadId, LeadPortfolio leadPortfolio) {

		Query query = new Query(Criteria.where("_id").is(agentId).and("leads.lead.$id").is(new ObjectId(leadId)));
		Update update = new Update();
		update.set("leads.$.contactStatus", leadPortfolio.getContactStatus());

		WriteResult writeResult = getMongoOperations().updateMulti(query, update, Agent.class);

		if (writeResult.getN() == 0) {
			throw new EntityNotUpdatedException("Agent");
		}

	}

}
