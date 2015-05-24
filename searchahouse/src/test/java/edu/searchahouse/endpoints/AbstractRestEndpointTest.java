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
import edu.searchahouse.model.Agent;
import edu.searchahouse.model.Lead;
import edu.searchahouse.model.Property;
import edu.searchahouse.model.Property.PropertyStatus;
import edu.searchahouse.model.Property.PropertyType;
import edu.searchahouse.model.repository.mongo.AgentRepository;
import edu.searchahouse.model.repository.mongo.LeadRepository;
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

	@Autowired
	protected LeadRepository leadRepository;

	@Autowired
	protected AgentRepository agentRepository;
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	protected void createPropertiesForTest() {
		propertyRepository.deleteAll();

		Arrays.asList("1,2".split(",")).forEach(
				index -> {
					Property property = new Property("Property" + index, "description" + index, "location" + index, 100000L, PropertyType.SALE,
							PropertyStatus.AVAILABLE);
					propertyRepository.save(property);
				});
	}

	protected void createLeadsForTest() {
		leadRepository.deleteAll();

		Arrays.asList("1,2".split(",")).forEach(index -> {
			Lead lead = new Lead("Lead" + index, "last name " + index, index + "lead@example.com", "012345678" + index);

			leadRepository.save(lead);
		});
	}

	protected void createAgentsForTest() {
		agentRepository.deleteAll();

		Arrays.asList("1,2".split(",")).forEach(index -> {
			Agent agent = new Agent("Agent" + index, "last name" + index, index + "agent@example.com");

			agentRepository.save(agent);
		});
	}

}
