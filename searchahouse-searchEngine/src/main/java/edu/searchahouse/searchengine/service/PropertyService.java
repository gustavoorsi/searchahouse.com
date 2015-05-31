package edu.searchahouse.searchengine.service;

import java.util.List;

import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import edu.searchahouse.searchengine.model.Property;

public interface PropertyService {

	public List<Property> findPropertiesByLocation(final GeoPoint geoPoint, final Double distance, final SortOrder sortOrder );

}
