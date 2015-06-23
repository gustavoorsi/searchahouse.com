package edu.searchahouse.searchengine.service.impl;

import java.util.List;

import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.GeoDistanceFilterBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import edu.searchahouse.searchengine.exceptions.EntityNotFoundException;
import edu.searchahouse.searchengine.model.Agent;
import edu.searchahouse.searchengine.model.Property;
import edu.searchahouse.searchengine.persistence.repository.elasticsearch.AgentRepository;
import edu.searchahouse.searchengine.persistence.repository.elasticsearch.PropertyRepository;
import edu.searchahouse.searchengine.service.PropertyService;

@Service
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final AgentRepository agentRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    @Autowired
    public PropertyServiceImpl(//
            final PropertyRepository propertyRepository, //
            final AgentRepository agentRepository, //
            final ElasticsearchOperations elasticsearchOperations //
    ) {
        this.propertyRepository = propertyRepository;
        this.agentRepository = agentRepository;
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    public List<Property> findPropertiesByLocation(final GeoPoint geoPoint, Double distance, final SortOrder sortOrder) {
        GeoDistanceFilterBuilder filter = FilterBuilders.geoDistanceFilter("location").point(geoPoint.getLat(), geoPoint.getLon())
                .distance(distance, DistanceUnit.KILOMETERS);

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withFilter(filter)
                .withSort(
                        SortBuilders.geoDistanceSort("location").point(geoPoint.getLat(), geoPoint.getLon())
                                .order(sortOrder == null ? SortOrder.ASC : sortOrder)).build();

        searchQuery.addIndices("searchahouse");
        searchQuery.addTypes("property");

        List<Property> properties = this.elasticsearchOperations.queryForList(searchQuery, Property.class);

        return properties;
    }

    @Override
    public List<Property> findPropertiesByAgentEmail(String email) {

        Agent agent = this.agentRepository.findByEmail(email);

        return (List<Property>) agent.getProperties();
    }
    
    @Override
    public List<Property> findPropertiesByAgentId(String agentId) {

        Agent agent = this.agentRepository.findById(agentId).orElseThrow( () -> new EntityNotFoundException("Agent") );

        return (List<Property>) agent.getProperties();
    }

	@Override
	public Property findPropertyById(String propertyId) {
		
		Property property = this.propertyRepository.findById(propertyId).orElseThrow( () -> new EntityNotFoundException("Property") );
		
		return property;
	}
    

}
