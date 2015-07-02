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
import edu.searchahouse.searchengine.rabbitmq.wrappers.AgentWrapperAmqp;
import edu.searchahouse.searchengine.rabbitmq.wrappers.LeadWrapperAmqp;
import edu.searchahouse.searchengine.rabbitmq.wrappers.PropertyWrapperAmqp;
import edu.searchahouse.searchengine.rabbitmq.wrappers.QueueOperation;
import edu.searchahouse.searchengine.service.PropertyService;

// we don't want to execute the listener while testing.
@Profile({ "!test", "!integrationTest" })
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
    public void receivePropertyMessage(PropertyWrapperAmqp<Property> wrapper) {
        if (QueueOperation.CREATE.equals(wrapper.getCrudOperation())) {
            createProperty(wrapper.getEntity());
        } else if (QueueOperation.DELETE.equals(wrapper.getCrudOperation())) {
            deleteProperty(wrapper.getEntity());
        } else if (QueueOperation.UPDATE.equals(wrapper.getCrudOperation())) {
            updateProperty(wrapper.getEntity());
        }
    }

    @RabbitListener(queues = "SEARCHAHOUSE-QUEUE-AGENT")
    public void receiveAgentMessage(AgentWrapperAmqp<Agent> wrapper) {
        if (QueueOperation.CREATE.equals(wrapper.getCrudOperation())) {
            createAgent(wrapper.getEntity());
        } else if (QueueOperation.DELETE.equals(wrapper.getCrudOperation())) {
            deleteAgent(wrapper.getEntity());
        } else if (QueueOperation.UPDATE.equals(wrapper.getCrudOperation())) {
            updateAgent(wrapper.getEntity());
        }
    }

    @RabbitListener(queues = "SEARCHAHOUSE-QUEUE-LEAD")
    public void receiveLeadMessage(LeadWrapperAmqp<Lead> wrapper) {
        if (QueueOperation.CREATE.equals(wrapper.getCrudOperation())) {
            createLead(wrapper.getEntity());
        } else if (QueueOperation.DELETE.equals(wrapper.getCrudOperation())) {
            deleteLead(wrapper.getEntity());
        } else if (QueueOperation.UPDATE.equals(wrapper.getCrudOperation())) {
            updateLead(wrapper.getEntity());
        }
    }

    private void createProperty(Property entity) {
        // first find out the geo point for the property address.
        Point gp = this.propertyService.findPointForAddress(entity.getAddress());
        entity.setLocation(new GeoPoint(gp.getX(), gp.getY()));

        propertyRepository.save(entity);
    }

    private void deleteProperty(final Property entity) {
        this.propertyRepository.delete(entity);
    }

    private void updateProperty(final Property entity) {
        // TODO:
    }

    private void createAgent(Agent entity) {
        this.agentRepository.save(entity);
    }

    private void deleteAgent(final Agent entity) {
        this.agentRepository.delete(entity);
    }

    private void updateAgent(final Agent entity) {
        // TODO:
    }

    private void createLead(Lead entity) {
        this.leadRepository.save(entity);
    }

    private void deleteLead(final Lead entity) {
        this.leadRepository.delete(entity);
    }

    private void updateLead(final Lead entity) {
        // TODO:
    }

}
