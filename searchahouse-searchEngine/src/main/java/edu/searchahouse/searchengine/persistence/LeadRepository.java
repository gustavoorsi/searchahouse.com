package edu.searchahouse.searchengine.persistence;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import edu.searchahouse.searchengine.model.Lead;

public interface LeadRepository extends ElasticsearchRepository<Lead, String> {

}
