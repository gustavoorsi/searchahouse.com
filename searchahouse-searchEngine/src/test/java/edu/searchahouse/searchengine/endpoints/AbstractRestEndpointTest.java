package edu.searchahouse.searchengine.endpoints;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import edu.searchahouse.searchengine.SearchEngineApplication;
import edu.searchahouse.searchengine.model.Property;
import edu.searchahouse.searchengine.model.Property.PropertyStatus;
import edu.searchahouse.searchengine.model.Property.PropertyType;
import edu.searchahouse.searchengine.persistence.repository.elasticsearch.PropertyRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SearchEngineApplication.class)
@ActiveProfiles(profiles = { "integrationTest" })
@WebIntegrationTest("server.port:0")
public class AbstractRestEndpointTest {

	protected MockMvc mockMvc;

	@Autowired
	protected WebApplicationContext webApplicationContext;

	@Autowired
	protected PropertyRepository propertyRepository;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	protected List<Property> createPropertiesForTest() {
		propertyRepository.deleteAll();

		List<Property> testProperties = new ArrayList<Property>();

		Arrays.asList("1,2,3".split(",")).forEach(
				index -> {
					Property property = new Property("Property" + index, "description" + index, new GeoPoint(Double.valueOf(index) + 10,
							-Double.valueOf(index) - 10), 100000L, PropertyType.SALE, PropertyStatus.AVAILABLE);
					property.setId(UUID.randomUUID().toString());
					propertyRepository.save(property);
					testProperties.add(property);
				});

		return testProperties;

	}

}
