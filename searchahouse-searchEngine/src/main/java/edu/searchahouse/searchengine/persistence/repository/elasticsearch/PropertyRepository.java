package edu.searchahouse.searchengine.persistence.repository.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import edu.searchahouse.searchengine.model.Property;

public interface PropertyRepository extends ElasticsearchRepository<Property, String> {

}
