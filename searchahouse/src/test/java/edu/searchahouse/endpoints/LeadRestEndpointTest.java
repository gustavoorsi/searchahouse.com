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

import edu.searchahouse.model.Lead;

public class LeadRestEndpointTest extends AbstractRestEndpointTest {

	private Lead aLead;

	@Before
	public void leadsForTest() {
		super.createLeadsForTest();

		aLead = leadRepository.findLeadByEmail("1lead@example.com").get();
	}

	@Test
	public void getLead_shouldReturn_lead_and_200_ok_httpcode() throws Exception {

		//@formatter:off
		mockMvc.perform(get( "/api/v1/lead/" + aLead.getPrimaryKey() ))
			.andExpect( status().isOk() )
			.andExpect( content().contentType( MediaTypes.HAL_JSON ) )
			.andExpect( jsonPath( "$.firstName", containsString("Lead1") ) )
			.andExpect( jsonPath( "$._links.self.href", endsWith("/lead/" + aLead.getPrimaryKey()) ) );
		//@formatter:on
	}

	@Test
	public void getLead_shouldReturn_404_notfound_httpcode() throws Exception {

		//@formatter:off
		mockMvc.perform(get( "/api/v1/lead/000000000000000000000000" ))
			.andExpect( status().isNotFound() )
			.andExpect( content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON) )
			.andExpect( jsonPath( "$[0].message",not( isEmptyString() )  ) );
		//@formatter:on
	}
	
	@Test
    public void getLead_shouldReturn_400_badRequest_httpcode() throws Exception {

        //@formatter:off
        mockMvc.perform(get( "/api/v1/lead/xxx" ))
            .andExpect( status().isBadRequest() );
        //@formatter:on
    }

	@Test
	public void getLeads_shouldReturn_two_leads_and_200_ok_httpcode() throws Exception {

		//@formatter:off
		mockMvc.perform(get( "/api/v1/lead" ))
			.andExpect( status().isOk() )
			.andExpect( content().contentType(MediaTypes.HAL_JSON) )
			.andExpect( jsonPath( "$._embedded.leadList", hasSize(2)) )
			.andExpect( jsonPath( "$._embedded.leadList[0].firstName", containsString("Lead1") ) )
			.andExpect( jsonPath( "$._embedded.leadList[1].firstName", containsString("Lead2") ) )
			.andExpect( jsonPath( "$._links.self.templated", is(true)) )
			.andExpect( jsonPath( "$._links.self.href", endsWith("/lead" + "{?page,size,sort}") ) );
		//@formatter:on
	}

	@Test
	public void createLead_shouldReturn_201_created_httpcode() throws Exception {

		//@formatter:off
		mockMvc.perform(post( "/api/v1/lead" )
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content( "{\"firstName\":\"Test lead\",\"lastName\":\"last name test\",\"email\":\"testemail@example.com\",\"mobilePhone\":\"123456\"}" ))
			.andExpect( status().isCreated() );
		//@formatter:on
	}
	
	@Test
	public void createLead_with_duplicate_email_shouldReturn_400_badrequest_httpcode() throws Exception {

		//@formatter:off
		mockMvc.perform(post( "/api/v1/lead" )
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content( "{\"firstName\":\"Test lead\",\"lastName\":\"last name test\",\"email\":\"1lead@example.com\"}" ))
			.andExpect( status().isBadRequest() )
			.andExpect( content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON) )
			.andExpect( jsonPath( "$[0].message", containsString("duplicate key") ) );
		//@formatter:on
	}

	@Test
	public void updateLead_shouldReturn_204_nocontent_httpcode() throws Exception {

		//@formatter:off
		mockMvc.perform(put( "/api/v1/lead/" +  aLead.getPrimaryKey() )
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content( "{\"firstName\":\"updated first name\",\"email\":\"updated@example.com\"}" ))
			.andExpect( status().isNoContent() );
		//@formatter:on
	}

}
