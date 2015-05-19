package edu.searchahouse.endpoints;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;

public class PropertyRestEndpointTest extends AbstractRestEndpointTest {

	@Test
	public void getProperty_shouldReturn_200ok_and_property() throws Exception {

		//@formatter:off
		mockMvc.perform(get( "/api/v1/property/" + p1.getId() ))
			.andExpect( status().isOk() )
			.andExpect( content().contentType( MediaTypes.HAL_JSON ) )
			.andExpect( jsonPath( "$.name", containsString("Property 1") ) )
			.andExpect( jsonPath( "$._links.self.href", endsWith("/property/" + p1.getId()) ) );
		//@formatter:on
	}

	@Test
	public void getProperty_shouldReturn_404_notfound() throws Exception {

		//@formatter:off
		mockMvc.perform(get( "/api/v1/property/xxx" ))
			.andExpect( status().isNotFound() )
			.andExpect( content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON) )
			.andExpect( jsonPath( "$[0].message",not( isEmptyString() )  ) );
		//@formatter:on
	}

}
