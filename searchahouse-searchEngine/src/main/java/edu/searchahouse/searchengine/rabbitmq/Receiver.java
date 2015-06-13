package edu.searchahouse.searchengine.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.searchahouse.searchengine.model.Agent;
import edu.searchahouse.searchengine.model.Lead;
import edu.searchahouse.searchengine.model.Property;
import edu.searchahouse.searchengine.persistence.repository.elasticsearch.AgentRepository;
import edu.searchahouse.searchengine.persistence.repository.elasticsearch.LeadRepository;
import edu.searchahouse.searchengine.persistence.repository.elasticsearch.PropertyRepository;

@Component
public class Receiver {

    private final AgentRepository agentRepository;
    private final PropertyRepository propertyRepository;
    private final LeadRepository leadRepository;

    @Autowired
    public Receiver(AgentRepository agentRepository, PropertyRepository propertyRepository, LeadRepository leadRepository) {
        this.agentRepository = agentRepository;
        this.propertyRepository = propertyRepository;
        this.leadRepository = leadRepository;
    }

    @RabbitListener(queues = "SEARCHAHOUSE-QUEUE-PROPERTY")
    public void receiveMessage(Property property) {
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
