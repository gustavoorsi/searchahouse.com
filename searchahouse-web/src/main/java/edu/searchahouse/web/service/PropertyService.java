package edu.searchahouse.web.service;

import edu.searchahouse.web.model.Property;

public interface PropertyService {

	/**
	 * Find a property by id
	 * 
	 * @param id
	 * @return
	 */
	Property findById(final String id);

}
