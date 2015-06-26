package edu.searchahouse.searchengine.service;

import java.util.List;

import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.geo.Point;

import edu.searchahouse.searchengine.model.Address;
import edu.searchahouse.searchengine.model.Property;

public interface PropertyService {

	public List<Property> findPropertiesByLocation(final GeoPoint geoPoint, final Double distance, final SortOrder sortOrder);

	public Page<Property> searchPropertiesByAddress(final String queryValue, final boolean isAutocompleteSearch, final Pageable pageable);

	public List<Property> findPropertiesByAgentEmail(final String email);

	public List<Property> findPropertiesByAgentId(String agentId);

	Property findPropertyById(final String propertyId);

	public Point findPointForAddress(final Address address);

}