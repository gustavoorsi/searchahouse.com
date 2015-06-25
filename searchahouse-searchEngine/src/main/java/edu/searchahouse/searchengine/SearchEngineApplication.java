package edu.searchahouse.searchengine;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.GeoDistanceFilterBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.client.RestTemplate;

import edu.searchahouse.searchengine.model.Agent;
import edu.searchahouse.searchengine.model.Lead;
import edu.searchahouse.searchengine.model.LeadPortfolio;
import edu.searchahouse.searchengine.model.Location;
import edu.searchahouse.searchengine.model.Property;
import edu.searchahouse.searchengine.model.Property.PropertyStatus;
import edu.searchahouse.searchengine.model.Property.PropertyType;
import edu.searchahouse.searchengine.persistence.repository.elasticsearch.AgentRepository;
import edu.searchahouse.searchengine.persistence.repository.elasticsearch.LeadRepository;
import edu.searchahouse.searchengine.persistence.repository.elasticsearch.LocationRepository;
import edu.searchahouse.searchengine.persistence.repository.elasticsearch.PropertyRepository;

@SpringBootApplication
public class SearchEngineApplication {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    public static void main(String[] args) {
        SpringApplication.run(SearchEngineApplication.class, args);
    }
    
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }

    @Profile("development")
    @Bean
    CommandLineRunner init(//
            final AgentRepository agentRepository, //
            final PropertyRepository propertyRepository, //
            final LeadRepository leadRepository, //
            final LocationRepository locationRepository //
    ) {

        agentRepository.deleteAll();
        propertyRepository.deleteAll();
        leadRepository.deleteAll();
        locationRepository.deleteAll();

        populateLocations(locationRepository);

        return (evt) -> Arrays.asList("1,2,4,5,6".split(",")).forEach(
                index -> {
                    Property property = new Property("Property" + index, "description" + index, new GeoPoint(1d, 1d), 100000L, PropertyType.SALE,
                            PropertyStatus.AVAILABLE);
                    property.setId(UUID.randomUUID().toString());
                    propertyRepository.save(property);

                    Lead lead = new Lead("Lead" + index, "last name " + index, index + "lead@example.com", "012345678" + index);
                    lead.setId(UUID.randomUUID().toString());
                    leadRepository.save(lead);

                    Agent agent = new Agent("Gustavo" + index, "Orsi" + index, index + "agent@example.com");
                    agent.setId(UUID.randomUUID().toString());

                    agent.addLead(new LeadPortfolio(lead));
                    agent.addProperty(property);

                    agentRepository.save(agent);

                    List<Agent> matchingAgents = agentRepository.findAutocompleteAgentsByFirstName("gus");

                    // ////////////////////////////////////////////////////
                    // get property by latitude and longitude and order asc
                    GeoDistanceFilterBuilder filter = FilterBuilders.geoDistanceFilter("location")
                            .point(property.getLocation().getLat(), property.getLocation().getLon()).distance(1, DistanceUnit.KILOMETERS);

                    SearchQuery searchQuery = new NativeSearchQueryBuilder()
                            .withFilter(filter)
                            .withSort(
                                    SortBuilders.geoDistanceSort("location").point(property.getLocation().getLat(), property.getLocation().getLon())
                                            .order(SortOrder.ASC)).build();

                    searchQuery.addIndices("searchahouse");
                    searchQuery.addTypes("property");

                    List<Property> properties = elasticsearchOperations.queryForList(searchQuery, Property.class);
                    // //////////////////////////////////////////////////

                    // //////////////////////////////////////////////////
                    // get properties by latitue and longitud
                    CriteriaQuery criteriaQuery = new CriteriaQuery(new Criteria("location").within(new GeoPoint(1, 1), "1km"));
                    criteriaQuery.addIndices("searchahouse");
                    criteriaQuery.addTypes("property");
                    properties = elasticsearchOperations.queryForList(criteriaQuery, Property.class);
                    // //////////////////////////////////////////////////

                    System.out.println("");

                });
    }

    private void populateLocations(final LocationRepository locationRepository) {
        
        final String USA = "usa";
        
        Location losAngeles = new Location( USA, "California", "Los Angeles", new GeoPoint(34.0194, -118.4108) );
        Location sanDiego = new Location( USA, "California", "San Diego", new GeoPoint(32.8153, -117.135) );
        Location sanFrancisco = new Location( USA, "California", "San Francisco", new GeoPoint(37.7751, -122.4193) );
        Location oakland = new Location( USA, "California", "Oakland", new GeoPoint(37.7699, -122.2256) );
        
        Location newYorkCity = new Location( USA, "New York", "New York City", new GeoPoint(40.6643, -73.9385) );
        Location yonkers = new Location( USA, "New York", "Yonkers", new GeoPoint(40.9459, -73.8674) );
        
        Location vancouver = new Location( USA, "Washington", "Vancouver", new GeoPoint(45.6372, -122.5965) );
        
        locationRepository.save( losAngeles );
        locationRepository.save( sanDiego );
        locationRepository.save( sanFrancisco );
        locationRepository.save( oakland );
        locationRepository.save( newYorkCity );
        locationRepository.save( yonkers );
        locationRepository.save( vancouver );
    }
}
