package edu.searchahouse.searchengine.persistence.repository.elasticsearch;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import edu.searchahouse.searchengine.model.Property;

public interface PropertyRepository extends ElasticsearchRepository<Property, String> {

	public Optional<Property> findById(final String propertyId);

	@Query(name = "search_property_autocomplete", value = "{\"multi_match\" : { \"query\" : \"?0\", \"fields\" : [\"address.state.autocomplete\",\"address.city.autocomplete\",\"address.street\"] } }")
	public Page<Property> findPropertiesByAutocompleteAddress(final String autocompleteAddress, Pageable pageable);

	public Page<Property> findByAddressStateOrAddressCityOrAddressStreet(final String stateOrCity, Pageable pageable);

}
