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
import org.springframework.data.geo.Point;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.searchahouse.searchengine.exceptions.EntityNotFoundException;
import edu.searchahouse.searchengine.model.Address;
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
    private final RestTemplate restTemplate;

    @Autowired
    public PropertyServiceImpl(//
            final PropertyRepository propertyRepository, //
            final AgentRepository agentRepository, //
            final ElasticsearchOperations elasticsearchOperations, //
            final RestTemplate restTemplate //
    ) {
        this.propertyRepository = propertyRepository;
        this.agentRepository = agentRepository;
        this.elasticsearchOperations = elasticsearchOperations;
        this.restTemplate = restTemplate;
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
    public List<Property> searchPropertiesByAddress(String queryValue) {
        List<Property> properties = this.propertyRepository.findPropertiesByAddress(queryValue);
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
	
	public Point findPointForAddress(final Address address) {

		Point p = null;

		try {
			final String googleApiUrl = "https://maps.googleapis.com/maps/api/geocode/json?address=" + address.getStreet() + "," + address.getCity() + "," + address.getState() + "&sensor=false&key=AIzaSyBP6jLAQF0fnGkIZDt5__OUNPhjWXj6Fbo";
			
//			final String googleApiUrl = "https://maps.googleapis.com/maps/api/geocode/json?address=545+macias,adrogue,buenos+aires,argentina&sensor=false&key=AIzaSyBP6jLAQF0fnGkIZDt5__OUNPhjWXj6Fbo";

			ResponseEntity<String> result = this.restTemplate.exchange(googleApiUrl, HttpMethod.GET, null, String.class);

			JsonNode jsonNode = new ObjectMapper().readValue(result.getBody(), JsonNode.class);

			Double lat = jsonNode.findPath("geometry").get("location").findValue("lat").asDouble();
			Double lon = jsonNode.findPath("geometry").get("location").findValue("lng").asDouble();

			p = new Point(lat, lon);

		} catch (Exception e) {

		}

		return p;
	}
    

}
