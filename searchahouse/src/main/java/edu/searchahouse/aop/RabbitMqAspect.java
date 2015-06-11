package edu.searchahouse.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.stereotype.Component;

import edu.searchahouse.configuration.RabbitMqProducerConfiguration;
import edu.searchahouse.model.BaseEntity;

@Component
@Aspect
public class RabbitMqAspect {

	private final RabbitMessagingTemplate rabbitMessagingTemplate;
	private final MappingJackson2MessageConverter mappingJackson2MessageConverter;

	@Autowired
	public RabbitMqAspect(//
			final RabbitMessagingTemplate rabbitMessagingTemplate, //
			final MappingJackson2MessageConverter mappingJackson2MessageConverter //
	) {
		this.rabbitMessagingTemplate = rabbitMessagingTemplate;
		this.mappingJackson2MessageConverter = mappingJackson2MessageConverter;
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

		this.rabbitMessagingTemplate.setMessageConverter(this.mappingJackson2MessageConverter);

		this.rabbitMessagingTemplate.convertAndSend(RabbitMqProducerConfiguration.amqpTopicExchange, RabbitMqProducerConfiguration.amqpQueue, entity);

	}
}