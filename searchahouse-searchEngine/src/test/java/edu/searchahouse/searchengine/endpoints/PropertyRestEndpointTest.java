package edu.searchahouse.searchengine.endpoints;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.hateoas.MediaTypes;

import edu.searchahouse.searchengine.model.Property;

public class PropertyRestEndpointTest extends AbstractRestEndpointTest {

	private List<Property> properties;

	@Before
	public void propertiesForTest() {
		properties = super.createPropertiesForTest();
	}

	@Test
	public void searchAllProperties_by_location_shouldReturn_all_properties_and_200_ok_httpcode() throws Exception {

		//@formatter:off
		mockMvc.perform(get( "/api/v1/property/location" )
						.param("longitude", "1")
						.param("latitude", "1")
						.param("distance", "100000")
						.param("sortOrder", "asc"))
			.andExpect( status().isOk() )
			.andExpect( content().contentType(MediaTypes.HAL_JSON) )
			.andExpect( jsonPath( "$._embedded.propertyList", hasSize(properties.size())) )
			.andExpect( jsonPath( "$._embedded.propertyList[0].name", containsString("Property1") ) )
			.andExpect( jsonPath( "$._embedded.propertyList[1].name", containsString("Property2") ) )
			.andExpect( jsonPath( "$._links.self.templated", is(true)) )
			.andExpect( jsonPath( "$._links.self.href", endsWith("/property/location" + "{?page,size,sort}") ) );
		//@formatter:on
	}

}
