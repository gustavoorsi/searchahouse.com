package edu.searchahouse.endpoints;

import java.util.Arrays;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import edu.searchahouse.SearchahouseApplication;
import edu.searchahouse.model.Property;
import edu.searchahouse.model.Property.PropertyStatus;
import edu.searchahouse.model.Property.PropertyType;
import edu.searchahouse.model.repository.mongo.PropertyRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SearchahouseApplication.class)
@ActiveProfiles(profiles = { "integrationTest" })
@WebIntegrationTest("server.port:0")
public class AbstractRestEndpointTest {

	protected MockMvc mockMvc;

	@Autowired
	protected WebApplicationContext webApplicationContext;

	@Autowired
	protected PropertyRepository propertyRepository;

	protected Property p1;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		
		propertyRepository.deleteAll();

		Arrays.asList("1,2".split(",")).forEach(
				index -> {
					Property property = new Property("Property" + index, "description" + index, "location" + index, 100000L, PropertyType.SALE,
							PropertyStatus.AVAILABLE);
					propertyRepository.save(property);
				});

		p1 = propertyRepository.findPropertyByName("Property1").get();

		System.out.println(p1);

	}

}
