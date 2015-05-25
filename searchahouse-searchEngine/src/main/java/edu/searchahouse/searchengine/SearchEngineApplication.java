package edu.searchahouse.searchengine;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import edu.searchahouse.searchengine.model.Agent;
import edu.searchahouse.searchengine.model.Lead;
import edu.searchahouse.searchengine.model.LeadPortfolio;
import edu.searchahouse.searchengine.model.Property;
import edu.searchahouse.searchengine.model.Property.PropertyStatus;
import edu.searchahouse.searchengine.model.Property.PropertyType;
import edu.searchahouse.searchengine.persistence.AgentRepository;
import edu.searchahouse.searchengine.persistence.LeadRepository;
import edu.searchahouse.searchengine.persistence.PropertyRepository;

@SpringBootApplication
public class SearchEngineApplication {
	
	@Autowired
	private ElasticsearchOperations elasticsearchOperations;

	public static void main(String[] args) {
		SpringApplication.run(SearchEngineApplication.class, args);
	}

	@Profile("development")
	@Bean
	CommandLineRunner init(//
			final AgentRepository agentRepository, //
			final PropertyRepository propertyRepository, //
			final LeadRepository leadRepository //
	) {

		agentRepository.deleteAll();
		propertyRepository.deleteAll();
		leadRepository.deleteAll();

		return (evt) -> Arrays.asList("1,2".split(",")).forEach(
				index -> {
					Property property = new Property("Property" + index, "description" + index, "location" + index, 100000L, PropertyType.SALE,
							PropertyStatus.AVAILABLE);
					 propertyRepository.save(property);

					Lead lead = new Lead("Lead" + index, "last name " + index, index + "lead@example.com", "012345678" + index);
					 leadRepository.save(lead);

					Agent agent = new Agent("Gustavo" + index, "Orsi" + index, index + "agent@example.com");

					agent.addLead(new LeadPortfolio(lead));
					agent.addProperty(property);

					agentRepository.save(agent);
					
					List<Agent> matchingAgents = agentRepository.findAutocompleteAgentsByFirstName("gus");
					
					
					System.out.println( "" );
					
				});

	}
}
