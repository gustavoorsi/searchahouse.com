package edu.searchahouse.endpoints;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;

import edu.searchahouse.model.Property;
import edu.searchahouse.model.Property.PropertyStatus;
import edu.searchahouse.model.Property.PropertyType;

public class PropertyRestEndpointTest extends AbstractRestEndpointTest {

    private Property p1;

    @Before
    public void propertiesForTest() {
        super.createPropertiesForTest();

        p1 = propertyRepository.findPropertyByName("Property1").get();
    }

    @Test
    public void getProperty_shouldReturn_property_and_200_ok_httpcode() throws Exception {

        //@formatter:off
		mockMvc.perform(get( "/api/v1/property/" + p1.getPrimaryKey() ))
			.andExpect( status().isOk() )
			.andExpect( content().contentType( MediaTypes.HAL_JSON ) )
			.andExpect( jsonPath( "$.name", containsString("Property1") ) )
			.andExpect( jsonPath( "$._links.self.href", endsWith("/property/" + p1.getPrimaryKey()) ) );
		//@formatter:on
    }

    @Test
    public void getProperty_shouldReturn_404_notfound_httpcode() throws Exception {

        //@formatter:off
		mockMvc.perform(get( "/api/v1/property/000000000000000000000000" ))
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
			.andExpect( jsonPath( "$._embedded.propertyList[0].name", containsString("Property1") ) )
			.andExpect( jsonPath( "$._embedded.propertyList[1].name", containsString("Property2") ) )
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
				.content( "{\"name\":\"Test property\",\"description\":\"some description\",\"address\":{\"state\":\"CA\",\"street\":\"a street\"},\"price\":50000,\"type\":\"SALE\",\"status\":\"AVAILABLE\"}" ))
			.andExpect( status().isCreated() );
		//@formatter:on
    }

    @Test
    public void updateProperty_shouldReturn_204_nocontent_httpcode() throws Exception {

        //@formatter:off
		mockMvc.perform(put( "/api/v1/property/" +  p1.getPrimaryKey() )
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content( "{\"name\":\"new name\",\"description\":\"some description\"}" ))
			.andExpect( status().isNoContent() );
		//@formatter:on
    }

    @Test
    public void updateProperty_shouldReturn_404_notfound_httpcode() throws Exception {

        //@formatter:off
		mockMvc.perform(put( "/api/v1/property/xxx" )
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content( "{\"name\":\"new name\",\"description\":\"some description\"}" ))
			.andExpect( status().isNotFound() )
			.andExpect( content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON) )
			.andExpect( jsonPath( "$[0].message",not( isEmptyString() )  ) );
		//@formatter:on
    }

    @Test
    public void delete_property_shouldReturn_200_ok_badrequest_httpcode() throws Exception {

        Property property = new Property("toBeDeleted", "toBeDeleted", null, 10L, PropertyType.SALE, PropertyStatus.AVAILABLE);
        property.setPrimaryKey(UUID.randomUUID().toString());
        super.propertyRepository.save(property);

        //@formatter:off
		mockMvc.perform( delete("/api/v1/property/" + property.getPrimaryKey()) )
			.andExpect( status().is2xxSuccessful() );
		//@formatter:on
    }

}
