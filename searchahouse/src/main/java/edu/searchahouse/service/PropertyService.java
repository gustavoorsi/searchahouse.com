
package edu.searchahouse.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Point;

import edu.searchahouse.model.Address;
import edu.searchahouse.model.Property;

public interface PropertyService {

	public Property findPropertyById(String id);
	
	public Page<Property> getPropertiesByPage( Pageable pageable );
	
	public Property save( Property input );
	
	public Property update( final String propertyId, Property input );
	
	public Point findPointForAddress( final Address address );

}
