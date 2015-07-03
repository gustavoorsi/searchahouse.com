package edu.searchahouse.leadrouter.endpoints;

import org.junit.Before;
import org.junit.Test;

public class LeadRouterRestEndpointTest extends AbstractRestEndpointTest {

	@Before
	public void propertiesForTest() {
	}

	@Test
	public void postLead_shouldReturn_201_created_httpcode() throws Exception {

		//@formatter:off
//		mockMvc.perform(post( "/api/v1/leadrouter" ).param("propertyId", "df7b700b-5569-4706-b5cd-b17eacd35d9a")
//				.contentType(MediaType.APPLICATION_JSON)
//				.accept(MediaType.APPLICATION_JSON)
//				.content( "{ \"firstName\":\"Lead 3\", \"lastName\":\"LastNameLead3\", \"email\":\"lead45@example.com\", \"mobilePhone\":\"123456\" }" ))
//			.andExpect( status().isCreated() );
		//@formatter:on
	}

}
