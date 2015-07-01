package edu.searchahouse.searchengine.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Component;

import edu.searchahouse.searchengine.model.Agent;
import edu.searchahouse.searchengine.model.Lead;
import edu.searchahouse.searchengine.model.Property;
import edu.searchahouse.searchengine.persistence.repository.elasticsearch.AgentRepository;
import edu.searchahouse.searchengine.persistence.repository.elasticsearch.LeadRepository;
import edu.searchahouse.searchengine.persistence.repository.elasticsearch.PropertyRepository;
import edu.searchahouse.searchengine.service.PropertyService;

// we don't want to execute the listener while testing.
@Profile({"!test","!integrationTest"})
@Component
public class Receiver {

	private final PropertyService propertyService;

	private final AgentRepository agentRepository;
	private final PropertyRepository propertyRepository;
	private final LeadRepository leadRepository;

	@Autowired
	public Receiver(AgentRepository agentRepository, PropertyRepository propertyRepository, LeadRepository leadRepository, final PropertyService propertyService) {
		this.agentRepository = agentRepository;
		this.propertyRepository = propertyRepository;
		this.leadRepository = leadRepository;
		this.propertyService = propertyService;
	}

	@RabbitListener(queues = "SEARCHAHOUSE-QUEUE-PROPERTY")
	public void receiveMessage(Property property) {

		// first find out the geo point for the property address.
		Point gp = this.propertyService.findPointForAddress(property.getAddress());
		property.setLocation( new GeoPoint(gp.getX(), gp.getY()) );

		propertyRepository.save(property);
	}

	@RabbitListener(queues = "SEARCHAHOUSE-QUEUE-AGENT")
	public void receiveMessage(Agent agent) {
		agentRepository.save(agent);
	}

	@RabbitListener(queues = "SEARCHAHOUSE-QUEUE-LEAD")
	public void receiveMessage(Lead lead) {
		leadRepository.save(lead);
	}

}
