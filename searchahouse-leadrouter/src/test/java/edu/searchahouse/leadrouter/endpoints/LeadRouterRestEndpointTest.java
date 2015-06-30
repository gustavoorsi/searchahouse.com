package edu.searchahouse.leadrouter.endpoints;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;

public class LeadRouterRestEndpointTest extends AbstractRestEndpointTest {

    @Before
    public void propertiesForTest() {
    }

    @Test
    public void postLead_shouldReturn_201_created_httpcode() throws Exception {

        //@formatter:off
//		mockMvc.perform(post( "/api/v1/leadrouter" ).param("propertyId", "559058dbbb33d1a2e026cb15")
//				.contentType(MediaType.APPLICATION_JSON)
//				.accept(MediaType.APPLICATION_JSON)
//				.content( "{ \"firstName\":\"Lead 1\", \"lastName\":\"LastNameLead1\", \"email\":\"lead1@example.com\", \"mobilePhone\":\"123456\" }" ))
//			.andExpect( status().isCreated() );
		//@formatter:on
    }

}
