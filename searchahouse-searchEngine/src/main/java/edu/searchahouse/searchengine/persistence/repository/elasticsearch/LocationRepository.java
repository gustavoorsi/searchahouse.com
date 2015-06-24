package edu.searchahouse.searchengine.persistence.repository.elasticsearch;

import java.util.List;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import edu.searchahouse.searchengine.model.Location;

public interface LocationRepository extends ElasticsearchRepository<Location, String> {

    @Query(name = "autocomplete_state", value = "{\"match\" : { \"state.autocomplete\" : { \"query\" : \"?0\", \"analyzer\" : \"standard\" } } }")
    public List<Location> findAutocompleteLocationState(final String autocompleteState);

}
