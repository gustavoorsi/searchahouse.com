package edu.searchahouse.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import edu.searchahouse.exceptions.EntityNotFoundException;
import edu.searchahouse.model.Address;
import edu.searchahouse.model.Property;
import edu.searchahouse.repository.mongo.PropertyRepository;
import edu.searchahouse.service.PropertyService;

@Service
public class PropertyServiceImpl extends BaseService implements PropertyService {

	private final PropertyRepository propertyRepository;
	private final RestTemplate restTemplate;
	

	@Autowired
	public PropertyServiceImpl(//
			final PropertyRepository propertyRepository, //
			final MongoOperations mongoOperations, //
			final RestTemplate restTemplate //
	) {
		super(mongoOperations);
		this.propertyRepository = propertyRepository;
		this.restTemplate = restTemplate;
	}

	@Override
	public Property findPropertyById(String id) {
		return this.propertyRepository.findPropertyById(new ObjectId(id)).orElseThrow(() -> new EntityNotFoundException("Property"));
	}

	@Override
	public Page<Property> getPropertiesByPage(Pageable pageable) {
		return this.propertyRepository.findAll(pageable);
	}

	@Override
	public Property save(Property input) {
		return this.propertyRepository.save(input);
	}

	@Override
	public Property update(String propertyId, Property input) {
		return (Property) super.update(propertyId, input);
	}
	
	public Point findPointForAddress( final Address address ){
	    
	    Point p = null;
	    
	    try {
    	    final String googleApiUrl = "https://maps.googleapis.com/maps/api/geocode/json?address=545+macias,adrogue,buenos+aires,argentina&sensor=false&key=AIzaSyBP6jLAQF0fnGkIZDt5__OUNPhjWXj6Fbo";
    	    
    	    ResponseEntity<String> result = this.restTemplate.exchange(googleApiUrl, HttpMethod.GET, null, String.class);
    	    
    	    JsonNode jsonNode = new ObjectMapper().readValue(result.getBody(), JsonNode.class);
    	    
    	    Double lat = jsonNode.findPath("geometry").get("location").findValue("lat").asDouble();
    	    Double lon = jsonNode.findPath("geometry").get("location").findValue("lat").asDouble();
    	    
    	    p = new Point(lat, lon);
    	    
	    } catch( Exception e ){
	        
	    }
	    
	    return p;
	}

}
