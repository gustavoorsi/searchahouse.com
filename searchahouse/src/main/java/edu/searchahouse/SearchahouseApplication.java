package edu.searchahouse;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;

import edu.searchahouse.model.Agent;
import edu.searchahouse.model.Lead;
import edu.searchahouse.model.LeadPortfolio;
import edu.searchahouse.model.Property;
import edu.searchahouse.model.Property.PropertyStatus;
import edu.searchahouse.model.Property.PropertyType;
import edu.searchahouse.model.repository.mongo.AgentRepository;
import edu.searchahouse.model.repository.mongo.LeadRepository;
import edu.searchahouse.model.repository.mongo.PropertyRepository;

@SpringBootApplication
@EnableAspectJAutoProxy
public class SearchahouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SearchahouseApplication.class, args);
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

		return (evt) -> Arrays.asList("1,2".split(",")).forEach(index -> {
			Property property = new Property("Property" + index, "description" + index, null, 100000L, PropertyType.SALE, PropertyStatus.AVAILABLE);
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
