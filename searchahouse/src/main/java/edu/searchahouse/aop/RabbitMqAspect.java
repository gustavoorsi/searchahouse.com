package edu.searchahouse.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.searchahouse.configuration.RabbitMqProducerConfiguration;
import edu.searchahouse.model.Agent;
import edu.searchahouse.model.BaseEntity;
import edu.searchahouse.model.BaseEntity.CrudOperation;
import edu.searchahouse.model.Lead;
import edu.searchahouse.model.Property;

@Component
@Aspect
public class RabbitMqAspect {

	private final QueueSender queueSender;

	@Autowired
	public RabbitMqAspect(//
			final QueueSender queueSender //
	) {
		this.queueSender = queueSender;
	}

	/**
	 * 
	 * Whenever an entity is updated, we want to trigger an update in elasticsearch. So we send a message to rabbitMq, later the search engine service will get
	 * this message to do what it wants.
	 * 
	 * @param entity
	 *            the saved or updated entity.
	 */
	@AfterReturning(value = "execution(* edu.searchahouse.repository.mongo.*.*(..))", returning = "entity")
	public void updateElasticsearch(final Property entity) {
		entity.setCrudOperation(CrudOperation.CREATE);
		convertAndSend(entity, RabbitMqProducerConfiguration.amqpQueueProperty);
	}

	/**
	 * 
	 * Whenever an entity is updated, we want to trigger an update in elasticsearch. So we send a message to rabbitMq, later the search engine service will get
	 * this message to do what it wants.
	 * 
	 * @param entity
	 *            the saved or updated entity.
	 */
	@AfterReturning(value = "execution(* edu.searchahouse.repository.mongo.*.*(..))", returning = "entity")
	public void updateElasticsearch(final Agent entity) {
		entity.setCrudOperation(CrudOperation.CREATE);
		convertAndSend(entity, RabbitMqProducerConfiguration.amqpQueueAgent);
	}

	@After(value = "execution(* org.springframework.data.repository.*.delete(..))")
	public void updateElasticsearch(final JoinPoint joinPoint) {

		BaseEntity entity = (BaseEntity) joinPoint.getArgs()[0];
		entity.setCrudOperation(CrudOperation.DELETE);

		if (entity instanceof Agent) {
			convertAndSend(entity, RabbitMqProducerConfiguration.amqpQueueAgent);
		} else if (entity instanceof Property) {
			convertAndSend(entity, RabbitMqProducerConfiguration.amqpQueueProperty);
		} else if (entity instanceof Lead) {
			convertAndSend(entity, RabbitMqProducerConfiguration.amqpQueueLead);
		}

	}

	/**
	 * 
	 * Whenever an entity is updated, we want to trigger an update in elasticsearch. So we send a message to rabbitMq, later the search engine service will get
	 * this message to do what it wants.
	 * 
	 * @param entity
	 *            the saved or updated entity.
	 */
	@AfterReturning(value = "execution(* edu.searchahouse.repository.mongo.*.*(..))", returning = "entity")
	public void updateElasticsearch(final Lead entity) {
		entity.setCrudOperation(CrudOperation.CREATE);
		convertAndSend(entity, RabbitMqProducerConfiguration.amqpQueueLead);
	}

	private void convertAndSend(final BaseEntity entity, final String routingKey) {
		this.queueSender.convertAndSend(entity, routingKey);
	}
}
