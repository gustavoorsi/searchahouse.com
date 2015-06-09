package edu.searchahouse.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.searchahouse.model.BaseEntity;

@Component
@Aspect
public class RabbitMqAspect {
	
	final static String queueName = "SEARCHAHOUSE-QUEUE"; // TODO: externalize this static field.

	private final RabbitTemplate rabbitTemplate;

	@Autowired
	public RabbitMqAspect(final RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	/**
	 * 
	 * Whenever an entity is updated, we want to trigger an update in elasticsearch. So we send a message to rabbitMq, later the search engine service will get
	 * this message to do what it wants.
	 * 
	 * @param entity
	 *            the saved or updated entity.
	 */
	@AfterReturning(value = "execution(* edu.searchahouse.model.repository.mongo.*.*(..))", returning = "entity")
	public void updateElasticsearch(final BaseEntity entity) {
		
		this.rabbitTemplate.convertAndSend(queueName, entity.toString());
		this.rabbitTemplate.convertAndSend(queueName, entity);

	}
}
