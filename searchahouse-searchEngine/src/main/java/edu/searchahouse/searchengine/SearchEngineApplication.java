package edu.searchahouse.searchengine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.web.client.RestTemplate;

import edu.searchahouse.searchengine.model.Address;
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

    final static Map<Integer, String[][]> VALUES = new HashMap<Integer, String[][]>();

    {
        VALUES.put(1, new String[][] { { "California", "Beverly Hills", "31340 Mulholland Dr." },
                { "http://www.hawthorne.co.nz/p7ssm_img_1/thumbs/Hawthorne-House-1_tmb.jpg" } }); // Anderson Pamela
        VALUES.put(2, new String[][] { { "California", "Bel Air", "846 Stradella Rd." },
                { "http://www.hawthorne.co.nz/p7ssm_img_1/fullsize/Hawthorne-House-4_fs.jpg" } }); // Eastwood Clint
        VALUES.put(3, new String[][] { { "California", "Hollywood Hills", "2705 Glen Dower Ave" },
                { "http://d3ka0sx7noujy3.cloudfront.net/wp-content/uploads/2011/10/byrne-reed-house-exterior.jpg" } }); // Pitt Brad
        VALUES.put(4, new String[][] { { "New York", "S Ozone Park", "134-12 Linden Blvd." },
                { "http://www.thehouseplanshop.com/userfiles/photos/large/2659053724d87558b4ad09.jpg" } });
        VALUES.put(5,
                new String[][] { { "New York", "Brooklyn", "379 Kings Hwy" }, { "https://pbs.twimg.com/profile_images/514415805652422657/VVLin2v4.png" } }); // Eastwood
                                                                                                                                                             // Clint
        VALUES.put(6, new String[][] { { "California", "Hollywood Hills", "2705 Glen Dower Ave" },
                { "http://www.avaay.com/wp-content/uploads/2015/04/bizarre-house-4.jpg" } }); // Pitt Brad
    }

    @Profile( "cleanStart" )
    @Bean
    CommandLineRunner deleteAll(//
            final AgentRepository agentRepository, //
            final PropertyRepository propertyRepository, //
            final LeadRepository leadRepository, //
            final LocationRepository locationRepository //
    ) {

        return (args) -> {
            agentRepository.deleteAll();
            propertyRepository.deleteAll();
            leadRepository.deleteAll();
            locationRepository.deleteAll();
        };

    }

    @Profile("development")
    @Bean
    CommandLineRunner init(//
            final AgentRepository agentRepository, //
            final PropertyRepository propertyRepository, //
            final LeadRepository leadRepository, //
            final LocationRepository locationRepository //
    ) {

        populateLocations(locationRepository);

        return (evt) -> Arrays.asList("1,2,4,5,6".split(",")).forEach(index -> {
            Property property = createProperty(index, propertyRepository);
            Lead lead = createLead(index, leadRepository);
            Agent agent = createAgent(index, agentRepository, property, lead);
        });
    }

    private void populateLocations(final LocationRepository locationRepository) {

        final String USA = "usa";

        Location losAngeles = new Location(USA, "California", "Los Angeles", new GeoPoint(34.0194, -118.4108));
        Location sanDiego = new Location(USA, "California", "San Diego", new GeoPoint(32.8153, -117.135));
        Location sanFrancisco = new Location(USA, "California", "San Francisco", new GeoPoint(37.7751, -122.4193));
        Location oakland = new Location(USA, "California", "Oakland", new GeoPoint(37.7699, -122.2256));

        Location newYorkCity = new Location(USA, "New York", "New York City", new GeoPoint(40.6643, -73.9385));
        Location yonkers = new Location(USA, "New York", "Yonkers", new GeoPoint(40.9459, -73.8674));

        Location vancouver = new Location(USA, "Washington", "Vancouver", new GeoPoint(45.6372, -122.5965));

        locationRepository.save(losAngeles);
        locationRepository.save(sanDiego);
        locationRepository.save(sanFrancisco);
        locationRepository.save(oakland);
        locationRepository.save(newYorkCity);
        locationRepository.save(yonkers);
        locationRepository.save(vancouver);
    }

    private Property createProperty(String index, final PropertyRepository propertyRepository) {

        Property property = new Property("Property" + index, "description" + index, new GeoPoint(1d, 1d), new Address(VALUES.get(Integer.valueOf(index))[0][0],
                VALUES.get(Integer.valueOf(index))[0][1], VALUES.get(Integer.valueOf(index))[0][2]), 100000L, PropertyType.SALE, PropertyStatus.AVAILABLE);
        property.setPrimaryKey(UUID.randomUUID().toString());
        property.setImageUrl(VALUES.get(Integer.valueOf(index))[1][0]);
        propertyRepository.save(property);

        return property;

    }

    private Lead createLead(String index, final LeadRepository leadRepository) {

        Lead lead = new Lead("Lead" + index, "last name " + index, index + "lead@example.com", "012345678" + index);
        lead.setPrimaryKey(UUID.randomUUID().toString());
        leadRepository.save(lead);

        return lead;
    }

    private Agent createAgent(String index, final AgentRepository agentRepository, Property property, Lead lead) {
        Agent agent = new Agent("Gustavo" + index, "Orsi" + index, index + "agent@example.com");
        agent.setPrimaryKey(UUID.randomUUID().toString());

        agent.addLead(new LeadPortfolio(lead));
        agent.addProperty(property);

        agentRepository.save(agent);

        return agent;
    }
}
