package edu.searchahouse.endpoints;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;

public class PropertyRestEndpointTest extends AbstractRestEndpointTest {

	@Test
	public void getProperty_shouldReturn_property_and_200_ok_httpcode() throws Exception {

		//@formatter:off
		mockMvc.perform(get( "/api/v1/property/" + p1.getId() ))
			.andExpect( status().isOk() )
			.andExpect( content().contentType( MediaTypes.HAL_JSON ) )
			.andExpect( jsonPath( "$.name", containsString("Property 1") ) )
			.andExpect( jsonPath( "$._links.self.href", endsWith("/property/" + p1.getId()) ) );
		//@formatter:on
	}

	@Test
	public void getProperty_shouldReturn_404_notfound_httpcode() throws Exception {

		//@formatter:off
		mockMvc.perform(get( "/api/v1/property/xxx" ))
			.andExpect( status().isNotFound() )
			.andExpect( content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON) )
			.andExpect( jsonPath( "$[0].message",not( isEmptyString() )  ) );
		//@formatter:on
	}

	@Test
	public void getProperties_shouldReturn_two_properties_and_200_ok_httpcode() throws Exception {

		//@formatter:off
		mockMvc.perform(get( "/api/v1/property" ))
			.andExpect( status().isOk() )
			.andExpect( content().contentType(MediaTypes.HAL_JSON) )
			.andExpect( jsonPath( "$._embedded.propertyList", hasSize(2)) )
			.andExpect( jsonPath( "$._embedded.propertyList[0].name", containsString("Property 1") ) )
			.andExpect( jsonPath( "$._embedded.propertyList[1].name", containsString("Property 2") ) )
			.andExpect( jsonPath( "$._links.self.templated", is(true)) )
			.andExpect( jsonPath( "$._links.self.href", endsWith("/property" + "{?page,size,sort}") ) );
		//@formatter:on
	}

	@Test
	public void createProperty_shouldReturn_201_created_httpcode() throws Exception {

		//@formatter:off
		mockMvc.perform(post( "/api/v1/property" )
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content( "{\"name\":\"Test property\",\"description\":\"some description\",\"location\":\"bsas\",\"price\":50000,\"type\":\"SALE\",\"status\":\"AVAILABLE\"}" ))
			.andExpect( status().isCreated() );
		//@formatter:on
	}

	@Test
	public void updateProperty_shouldReturn_204_nocontent_httpcode() throws Exception {

		//@formatter:off
		mockMvc.perform(put( "/api/v1/property/" +  p1.getId() )
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content( "{\"name\":\"Property 1\",\"description\":\"some description\",\"location\":\"bsas\",\"price\":50000,\"type\":\"SALE\",\"status\":\"AVAILABLE\"}" ))
			.andExpect( status().isNoContent() );
		//@formatter:on
	}

}
