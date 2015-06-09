package edu.searchahouse.searchengine.rabbitmq;

import org.springframework.stereotype.Component;

@Component
public class Receiver {
	
	public void receiveMessage(Object message) {
		System.out.println("Received <" + message + ">");
	}

}
