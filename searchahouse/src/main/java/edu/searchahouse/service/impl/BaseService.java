package edu.searchahouse.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import edu.searchahouse.model.BaseEntity;

public abstract class BaseService {

	private final MongoTemplate mongoTemplate;

	@Autowired
	public BaseService(final MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	protected BaseEntity update(String agentId, BaseEntity inputEntity) {

		Query query = new Query(Criteria.where("_id").is(agentId));
		Update update = createUpdate(inputEntity);

		this.mongoTemplate.upsert(query, update, BaseEntity.class);

		inputEntity.setId(agentId);

		return inputEntity;
	}

	private Update createUpdate(final BaseEntity entity) {
		Update update = new Update();

		entity.toMap().forEach((k, v) -> update.set(k, v));

		return update;
	}

	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

}
