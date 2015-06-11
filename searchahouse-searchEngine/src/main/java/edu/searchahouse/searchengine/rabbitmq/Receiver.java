package edu.searchahouse.searchengine.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import edu.searchahouse.searchengine.model.Agent;
import edu.searchahouse.searchengine.model.Lead;
import edu.searchahouse.searchengine.model.LeadPortfolio;
import edu.searchahouse.searchengine.model.Property;

@Component
public class Receiver {

	@RabbitListener(queues = "SEARCHAHOUSE-QUEUE-ENTITIES")
	public void receiveMessage(Property message) {
		System.out.println("Received <" + message + ">");
	}

	public void receiveMessage(Agent message) {
		System.out.println("Received <" + message + ">");
	}

	public void receiveMessage(Lead message) {
		System.out.println("Received <" + message + ">");
	}

	public void receiveMessage(LeadPortfolio message) {
		System.out.println("Received <" + message + ">");
	}

}
