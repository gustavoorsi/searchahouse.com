package edu.searchahouse;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import edu.searchahouse.model.Property;
import edu.searchahouse.model.Property.PropertyStatus;
import edu.searchahouse.model.Property.PropertyType;
import edu.searchahouse.model.repository.mongo.PropertyRepository;

/**
 * 
 * @author Gustavo Orsi
 *
 */
@Configuration
@Profile(value = "integrationTest")
public class SearchahouseApplicationTest implements CommandLineRunner {

	@Autowired
	private PropertyRepository propertyRepository;

	@Override
	public void run(String... args) throws Exception {

		propertyRepository.deleteAll();

		Property p1 = new Property("Property 1", "description 1", "location 1", 100000L, PropertyType.SALE,
				PropertyStatus.AVAILABLE);

		Property p2 = new Property("Property 2", "description 2", "location 2", 200000L, PropertyType.RENT,
				PropertyStatus.NOT_AVAILABLE);

		// save a couple of properties
		propertyRepository.save(p1);
		propertyRepository.save(p2);

	}

	@PreDestroy
	public void cleanUp() {
		propertyRepository.deleteAll();
	}

}
