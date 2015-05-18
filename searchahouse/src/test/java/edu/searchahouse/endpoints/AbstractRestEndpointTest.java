
package edu.searchahouse.endpoints;

import java.util.Optional;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
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
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class AbstractRestEndpointTest {

	protected MockMvc mockMvc;

	@Autowired
	protected WebApplicationContext webApplicationContext;

	protected PropertyRepository propertyRepositoryMock;

	@Before
	public void setup() {
		propertyRepositoryMock = Mockito.mock( PropertyRepository.class );
		Mockito.reset(propertyRepositoryMock);
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	protected Optional<Property> getMockProperty() {
		Property p = new Property("Beach House", "A nice house by the beach", "California", 100000L, PropertyType.SALE,
				PropertyStatus.AVAILABLE);
		return Optional.of(p);
	}

}
