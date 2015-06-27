package edu.searchahouse.endpoints;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;

import edu.searchahouse.model.Agent;
import edu.searchahouse.model.Lead;
import edu.searchahouse.model.LeadPortfolio;
import edu.searchahouse.model.Property;

public class AgentRestEndpointTest extends AbstractRestEndpointTest {

	private Agent anAgent;
	private Property aProperty;
	private Lead aLead;

	@Before
	public void agentsForTest() {
		super.createAgentsForTest();
		super.createLeadsForTest();
		super.createPropertiesForTest();
		anAgent = agentRepository.findAgentByEmail("1agent@example.com").get();
		aProperty = propertyRepository.findPropertyByName("Property1").get();
		aLead = leadRepository.findLeadByEmail("1lead@example.com").get();
	}

	@Test
	public void getAgent_shouldReturn_agent_and_200_ok_httpcode() throws Exception {

		//@formatter:off
		mockMvc.perform(get( "/api/v1/agent/" + anAgent.getPrimaryKey() ))
			.andExpect( status().isOk() )
			.andExpect( content().contentType( MediaTypes.HAL_JSON ) )
			.andExpect( jsonPath( "$.firstName", containsString("Agent1") ) )
			.andExpect( jsonPath( "$._links.self.href", endsWith("/agent/" + anAgent.getPrimaryKey() + "?lazy=false") ) );
		//@formatter:on
	}

	@Test
	public void getAgent_shouldReturn_404_notfound_httpcode() throws Exception {

		//@formatter:off
		mockMvc.perform(get( "/api/v1/agent/000000000000000000000000" ))
			.andExpect( status().isNotFound() )
			.andExpect( content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON) )
			.andExpect( jsonPath( "$[0].message",not( isEmptyString() )  ) );
		//@formatter:on
	}
	
	@Test
	public void getAgent_shouldReturn_400_badrequest_httpcode() throws Exception {

		//@formatter:off
		mockMvc.perform(get( "/api/v1/agent/xxx" ))
			.andExpect( status().isBadRequest() );
		//@formatter:on
	}
	
	@Test
	public void getAgents_shouldReturn_two_agents_and_200_ok_httpcode() throws Exception {

		//@formatter:off
		mockMvc.perform(get( "/api/v1/agent" ))
			.andExpect( status().isOk() )
			.andExpect( content().contentType(MediaTypes.HAL_JSON) )
			.andExpect( jsonPath( "$._embedded.agentList", hasSize(2)) )
			.andExpect( jsonPath( "$._embedded.agentList[0].firstName", containsString("Agent1") ) )
			.andExpect( jsonPath( "$._embedded.agentList[1].firstName", containsString("Agent2") ) )
			.andExpect( jsonPath( "$._links.self.templated", is(true)) )
			.andExpect( jsonPath( "$._links.self.href", endsWith("/agent" + "{?page,size,sort}") ) );
		//@formatter:on
	}

	@Test
	public void createAgent_shouldReturn_201_created_httpcode() throws Exception {

		//@formatter:off
		mockMvc.perform(post( "/api/v1/agent" )
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content( "{\"firstName\":\"Test agent\",\"lastName\":\"last name test\",\"email\":\"testemail@example.com\"}" ))
			.andExpect( status().isCreated() );
		//@formatter:on
	}
	
	@Test
	public void createAgent_invalid_input_data_shouldReturn_400_badrequest_httpcode() throws Exception {

		// missing first name.
		
		//@formatter:off
		mockMvc.perform(post( "/api/v1/agent" )
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content( "{\"lastName\":\"last name test\",\"email\":\"testemail@example.com\"}" ))
			.andExpect( status().isBadRequest() )
			.andExpect( jsonPath( "$[0].message", not( isEmptyString())) );
		//@formatter:on
	}

	@Test
	public void createAgent_with_duplicate_email_shouldReturn_400_badrequest_httpcode() throws Exception {

		//@formatter:off
		mockMvc.perform(post( "/api/v1/agent" )
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content( "{\"firstName\":\"Test agent\",\"lastName\":\"last name test\",\"email\":\"1agent@example.com\"}" ))
			.andExpect( status().isBadRequest() )
			.andExpect( content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON) )
			.andExpect( jsonPath( "$[0].message", containsString("duplicate key") ) );
		//@formatter:on
	}

	@Test
	public void updateAgent_shouldReturn_204_nocontent_httpcode() throws Exception {

		//@formatter:off
		mockMvc.perform(put( "/api/v1/agent/" +  anAgent.getPrimaryKey() )
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content( "{\"firstName\":\"updated first name\",\"email\":\"updated@example.com\"}" ))
			.andExpect( status().isNoContent() );
		//@formatter:on
	}

	@Test
	public void updateAgent_shouldReturn_404_notfound_httpcode() throws Exception {

		//@formatter:off
		mockMvc.perform(put( "/api/v1/agent/xxx" )
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content( "{\"firstName\":\"updated first name\",\"email\":\"updated@example.com\"}" ))
			.andExpect( status().isNotFound() )
			.andExpect( content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON) )
			.andExpect( jsonPath( "$[0].message",not( isEmptyString() )  ) );
		//@formatter:on
	}

	@Test
	public void addProperty_shouldReturn_204_nocontent_httpcode() throws Exception {

		//@formatter:off
		mockMvc.perform(put( "/api/v1/agent/" +  anAgent.getPrimaryKey() + "/property/" + aProperty.getPrimaryKey() )
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content( "" ))
			.andExpect( status().isNoContent() );
//			.andExpect( header().string("location", "") );
		//@formatter:on
	}

	@Test
	public void change_lead_contactedStatus_shouldReturn_204_nocontent_httpcode() throws Exception {

		anAgent.addLead(new LeadPortfolio(aLead));
		agentRepository.save(anAgent);

		//@formatter:off
		mockMvc.perform(put( "/api/v1/agent/" + anAgent.getPrimaryKey() + "/lead/" + aLead.getPrimaryKey() )
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content( "{\"contactStatus\": \"CONTACTED\"}" ))
			.andExpect( status().isNoContent() );
		//@formatter:on
	}

	@Test
	public void change_lead_contactedStatus_shouldReturn_400_badrequest_httpcode() throws Exception {

		//@formatter:off
		mockMvc.perform(put( "/api/v1/agent/" + anAgent.getPrimaryKey() + "/lead/" + aLead.getPrimaryKey() )
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content( "{\"contactStatus\": \"CONTACTED\"}" ))
			.andExpect( status().isBadRequest() );
		//@formatter:on
	}

}
