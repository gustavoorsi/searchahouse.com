package edu.searchahouse;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

import edu.searchahouse.model.Address;
import edu.searchahouse.model.Agent;
import edu.searchahouse.model.Lead;
import edu.searchahouse.model.LeadPortfolio;
import edu.searchahouse.model.Property;
import edu.searchahouse.model.Property.PropertyStatus;
import edu.searchahouse.model.Property.PropertyType;
import edu.searchahouse.repository.mongo.AgentRepository;
import edu.searchahouse.repository.mongo.LeadRepository;
import edu.searchahouse.repository.mongo.PropertyRepository;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableAsync
public class SearchahouseApplication {

    final static Map<Integer, String[]> ADDRESSES = new HashMap<Integer, String[]>();

    {
        ADDRESSES.put(1, new String[] { "California", "Beverly Hills", "31340 Mulholland Dr." }); // Anderson Pamela
        ADDRESSES.put(2, new String[] { "California", "Bel Air", "846 Stradella Rd." }); // Eastwood Clint
        ADDRESSES.put(3, new String[] { "California", "Hollywood Hills", "2705 Glen Dower Ave" }); // Pitt Brad
    }

    public static void main(String[] args) {
        SpringApplication.run(SearchahouseApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }

    @Profile("development")
    @Bean
    CommandLineRunner init(//
            final PropertyRepository propertyRepository, //
            final LeadRepository leadRepository, //
            final AgentRepository agentRepository//
    ) {

        propertyRepository.deleteAll();
        leadRepository.deleteAll();
        agentRepository.deleteAll();

        return (evt) -> Arrays.asList("1,2,3".split(",")).forEach(
                index -> {
                    Property property = new Property("Property" + index, "description" + index, new Address(ADDRESSES.get(Integer.valueOf(index))[0], ADDRESSES
                            .get(Integer.valueOf(index))[1], ADDRESSES.get(Integer.valueOf(index))[2]), 100000L, PropertyType.SALE, PropertyStatus.AVAILABLE);
                    propertyRepository.save(property);

                    Lead lead = new Lead("Lead" + index, "last name " + index, index + "lead@example.com", "012345678" + index);
                    leadRepository.save(lead);

                    Agent agent = new Agent("Gustavo" + index, "Orsi" + index, index + "agent@example.com");

                    agent.addLead(new LeadPortfolio(lead));
                    agent.addProperty(property);

                    agentRepository.save(agent);
                });

    }
}
