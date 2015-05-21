package edu.searchahouse;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import edu.searchahouse.model.Property;
import edu.searchahouse.model.Property.PropertyStatus;
import edu.searchahouse.model.Property.PropertyType;
import edu.searchahouse.model.repository.mongo.PropertyRepository;

@SpringBootApplication
public class SearchahouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SearchahouseApplication.class, args);
	}

	@Profile("development")
	@Bean
	CommandLineRunner init(final PropertyRepository propertyRepository) {
		
		propertyRepository.deleteAll();
		
		return (evt) -> Arrays.asList("1,2".split(",")).forEach(
				index -> {
					Property property = new Property("Property" + index, "description" + index, "location" + index, 100000L,
							PropertyType.SALE, PropertyStatus.AVAILABLE);
					propertyRepository.save(property);
				});
	}

}
