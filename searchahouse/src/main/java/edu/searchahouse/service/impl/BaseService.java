package edu.searchahouse.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;

import edu.searchahouse.exceptions.EntityNotFoundException;
import edu.searchahouse.model.BaseEntity;

public abstract class BaseService {

	private final MongoOperations mongoOperations;

	@Autowired
	public BaseService(final MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}

	protected BaseEntity update(String entityId, BaseEntity inputEntity) {

		Query query = new Query(Criteria.where("_id").is(entityId));
		Update update = createUpdate(inputEntity);

		WriteResult writeResult = this.mongoOperations.updateFirst(query, update, inputEntity.getClass());

		if (writeResult.getN() == 0) {
			throw new EntityNotFoundException( inputEntity.getClass().getSimpleName() );
		}

		inputEntity.setId(entityId);

		return inputEntity;
	}

	private Update createUpdate(final BaseEntity entity) {
		Update update = new Update();
		
		entity.toMap().forEach((k, v) -> update.set(k, v));

		return update;
	}

	public MongoOperations getMongoOperations() {
		return mongoOperations;
	}

}
