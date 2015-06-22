package edu.searchahouse.service.impl;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import edu.searchahouse.exceptions.EntityNotFoundException;
import edu.searchahouse.model.Lead;
import edu.searchahouse.repository.mongo.LeadRepository;
import edu.searchahouse.service.LeadService;

@Service
public class LeadServiceImpl extends BaseService implements LeadService {

	private final LeadRepository leadRepository;

	@Autowired
	public LeadServiceImpl(//
			final LeadRepository leadRepository, //
			final MongoOperations mongoOperations//
	) {
		super(mongoOperations);
		this.leadRepository = leadRepository;
	}

	@Override
	public Lead findLeadById(String id) {
		return this.leadRepository.findLeadById(new ObjectId(id)).orElseThrow(() -> new EntityNotFoundException("Lead"));
	}

	@Override
	public Page<Lead> getLeadsByPage(Pageable pageable) {
		return this.leadRepository.findAll(pageable);
	}

	@Override
	public Lead save(Lead input) {
		return this.leadRepository.save(input);
	}

	@Override
	public Lead update(String leadId, Lead input) {
		return (Lead) super.update(leadId, input);
	}

}
