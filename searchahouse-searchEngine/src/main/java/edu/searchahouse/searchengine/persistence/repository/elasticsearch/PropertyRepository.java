package edu.searchahouse.searchengine.persistence.repository.elasticsearch;

import java.util.List;
import java.util.Optional;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import edu.searchahouse.searchengine.model.Property;

public interface PropertyRepository extends ElasticsearchRepository<Property, String> {

    public Optional<Property> findById(final String propertyId);

//    @Query(name = "search_property_autocomplete", value = "{\"match\" : { \"firstName.autocomplete\" : { \"query\" : \"?0\", \"analyzer\" : \"standard\" } } }")
    @Query(name = "search_property_autocomplete", value = "{\"multi_match\" : { \"query\" : \"?0\", \"fields\" : [\"address.state.autocomplete\",\"address.city.autocomplete\",\"address.street\"] } }")
    public List<Property> findPropertiesByAddress(final String autocompleteAddress);

}
