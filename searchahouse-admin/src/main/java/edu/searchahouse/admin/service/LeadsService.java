package edu.searchahouse.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.searchahouse.admin.model.Lead;

public interface LeadsService {

	Page<Lead> findAllLeads(final Pageable pageable);

	void deleteLead(final String leadId);

}
