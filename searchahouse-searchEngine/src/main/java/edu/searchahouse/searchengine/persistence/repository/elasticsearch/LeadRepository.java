package edu.searchahouse.searchengine.persistence.repository.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import edu.searchahouse.searchengine.model.Lead;

public interface LeadRepository extends ElasticsearchRepository<Lead, String> {

}
