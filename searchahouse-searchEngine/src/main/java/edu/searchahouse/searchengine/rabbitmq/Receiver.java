package edu.searchahouse.searchengine.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import edu.searchahouse.searchengine.model.Agent;
import edu.searchahouse.searchengine.model.Lead;
import edu.searchahouse.searchengine.model.Property;

@Component
public class Receiver {

	@RabbitListener(queues = "SEARCHAHOUSE-QUEUE-PROPERTY")
	public void receiveMessage(Property message) {
		System.out.println("Received Property<" + message + ">");
	}

	@RabbitListener(queues = "SEARCHAHOUSE-QUEUE-AGENT")
	public void receiveMessage(Agent message) {
		System.out.println("Received Agent<" + message + ">");
	}

	@RabbitListener(queues = "SEARCHAHOUSE-QUEUE-LEAD")
	public void receiveMessage(Lead message) {
		System.out.println("Received Lead<" + message + ">");
	}

}
