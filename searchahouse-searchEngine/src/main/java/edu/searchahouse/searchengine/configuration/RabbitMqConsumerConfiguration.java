package edu.searchahouse.searchengine.configuration;

import java.util.HashMap;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import edu.searchahouse.searchengine.model.Agent;
import edu.searchahouse.searchengine.model.Lead;
import edu.searchahouse.searchengine.model.LeadPortfolio;
import edu.searchahouse.searchengine.model.Property;
import edu.searchahouse.searchengine.rabbitmq.Receiver;

@Configuration
@EnableRabbit
public class RabbitMqConsumerConfiguration {

	final static String queueName = "SEARCHAHOUSE-QUEUE";

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Bean
	Queue queue() {
		return new Queue(queueName, false);
	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueName);
		container.setMessageListener(listenerAdapter);
		return container;
	}
	
	@Bean
	public MessageConverter jsonMessageConverter() {
		Jackson2JsonMessageConverter jsonMessageConverter = new Jackson2JsonMessageConverter();
		jsonMessageConverter.setClassMapper( typeMapper() );
	    return jsonMessageConverter;
	}
	
	@Bean
	public DefaultClassMapper typeMapper() {
		DefaultClassMapper typeMapper = new DefaultClassMapper();
		HashMap<String, Class<?>> idClassMapping = new HashMap<String, Class<?>>();
		idClassMapping.put("property", Property.class);
		idClassMapping.put("agent", Agent.class);
		idClassMapping.put("lead", Lead.class);
		idClassMapping.put("leadPortfolio", LeadPortfolio.class);
		typeMapper.setIdClassMapping(idClassMapping);
		return typeMapper;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(Receiver receiver, MessageConverter jsonMessageConverter) {
		MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(receiver, "receiveMessage");
		messageListenerAdapter.setMessageConverter(jsonMessageConverter);
		return messageListenerAdapter; 
	}

}
