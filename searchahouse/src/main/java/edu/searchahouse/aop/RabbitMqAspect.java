package edu.searchahouse.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.searchahouse.model.Agent;
import edu.searchahouse.model.BaseEntity;
import edu.searchahouse.model.Lead;
import edu.searchahouse.model.Property;

@Component
@Aspect
public class RabbitMqAspect {

	final static String queueName = "SEARCHAHOUSE-QUEUE"; // TODO: externalize this static field.

	private final RabbitTemplate rabbitTemplate;

	private final MessageConverter jsonMessageConverter;

	@Autowired
	public RabbitMqAspect(final RabbitTemplate rabbitTemplate, final MessageConverter jsonMessageConverter) {
		this.rabbitTemplate = rabbitTemplate;
		this.jsonMessageConverter = jsonMessageConverter;
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

		this.rabbitTemplate.setMessageConverter(this.jsonMessageConverter);

		this.rabbitTemplate.convertAndSend(queueName, entity, (m) -> {
			if (entity instanceof Property) {
				m.getMessageProperties().setHeader("__TypeId__", "property");
			} else if (entity instanceof Agent) {
				m.getMessageProperties().setHeader("__TypeId__", "agent");
			} else if (entity instanceof Lead) {
				m.getMessageProperties().setHeader("__TypeId__", "lead");
			}
			return m;
		});

	}
}
