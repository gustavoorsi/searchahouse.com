package edu.searchahouse.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

@Configuration
public class RabbitMqProducerConfiguration {

	public final static String amqpQueueProperty = "SEARCHAHOUSE-QUEUE-PROPERTY";
	public final static String amqpQueueAgent = "SEARCHAHOUSE-QUEUE-AGENT";
	public final static String amqpQueueLead = "SEARCHAHOUSE-QUEUE-LEAD";
	public final static String amqpTopicExchange = "crudmicroservice.entities.updated";

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Bean
	Queue queueProperty() {
		return new Queue(amqpQueueProperty, false);
	}
	
	@Bean
	Queue queueAgent() {
		return new Queue(amqpQueueAgent, false);
	}
	
	@Bean
	Queue queueLead() {
		return new Queue(amqpQueueLead, false);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(amqpTopicExchange);
	}

	@Bean
	Binding bindingExchangeAndProperty(Queue queueProperty, TopicExchange exchange) {
		return BindingBuilder.bind(queueProperty).to(exchange).with(amqpQueueProperty);
	}
	
	@Bean
	Binding bindingExchangeAndLead(Queue queueLead, TopicExchange exchange) {
		return BindingBuilder.bind(queueLead).to(exchange).with(amqpQueueLead);
	}
	
	@Bean
	Binding bindingExchangeAndAgent(Queue queueAgent, TopicExchange exchange) {
		return BindingBuilder.bind(queueAgent).to(exchange).with(amqpQueueAgent);
	}

	@Bean
	public MappingJackson2MessageConverter jackson2Converter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		return converter;
	}

}
