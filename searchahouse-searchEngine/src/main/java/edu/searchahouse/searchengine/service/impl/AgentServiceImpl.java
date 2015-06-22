package edu.searchahouse.searchengine.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import edu.searchahouse.searchengine.model.Agent;
import edu.searchahouse.searchengine.persistence.repository.elasticsearch.AgentRepository;
import edu.searchahouse.searchengine.service.AgentService;

@Service
public class AgentServiceImpl implements AgentService {

	private final AgentRepository agentRepository;

	private final ElasticsearchOperations elasticsearchOperations;

	@Autowired
	public AgentServiceImpl(//
			final AgentRepository agentRepository, //
			final ElasticsearchOperations elasticsearchOperations //
	) {
		this.agentRepository = agentRepository;
		this.elasticsearchOperations = elasticsearchOperations;
	}

	@Override
	public List<Agent> findAgentsByFirstName(final String autocompleteFirstName) {
		
		List<Agent> agents = this.agentRepository.findAutocompleteAgentsByFirstName(autocompleteFirstName);
		
		return agents;
	}

	// @Override
	// public List<Property> findPropertiesByLocation(final GeoPoint geoPoint, Double distance, final SortOrder sortOrder) {
	// GeoDistanceFilterBuilder filter = FilterBuilders.geoDistanceFilter("location").point(geoPoint.getLat(), geoPoint.getLon())
	// .distance(distance, DistanceUnit.KILOMETERS);
	//
	// SearchQuery searchQuery = new NativeSearchQueryBuilder().withFilter(filter)
	// .withSort(SortBuilders.geoDistanceSort("location").point(geoPoint.getLat(), geoPoint.getLon()).order(sortOrder == null ? SortOrder.ASC :
	// sortOrder)).build();
	//
	// searchQuery.addIndices("searchahouse");
	// searchQuery.addTypes("property");
	//
	// List<Property> properties = elasticsearchOperations.queryForList(searchQuery, Property.class);
	//
	// return properties;
	// }

}
