package edu.searchahouse.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.searchahouse.model.Lead;

public interface LeadService {

	public Lead findLeadById(String id);

	public Page<Lead> getLeadsByPage(Pageable pageable);

	public Lead save(Lead input);

	public Lead update(final String leadId, Lead input);

}
