package edu.searchahouse.searchengine.endpoints.hal.resources.assemblers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Component;

import edu.searchahouse.searchengine.endpoints.AgentRestEndpoint;
import edu.searchahouse.searchengine.endpoints.PropertyRestEndpoint;
import edu.searchahouse.searchengine.model.Agent;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class AgentResourceAssembler implements ResourceAssembler<Agent, ResourceSupport> {

    private final PropertyResourceAssembler propertyResourceAssembler;
    private final LeadResourceAssembler leadResourceAssember;

    @Autowired
    public AgentResourceAssembler(PropertyResourceAssembler propertyResourceAssembler, LeadResourceAssembler leadResourceAssember) {
        this.propertyResourceAssembler = propertyResourceAssembler;
        this.leadResourceAssember = leadResourceAssember;
    }

    @Override
    public ResourceSupport toResource(Agent entity) {

        // add link to itself ( rel = self )
        // TODO: get the endpoint url that GET a property from micro-service CRUD. (using eureka). For now just hardcode the domain.
        // add link to itself ( rel = self )
        Link selfLink = linkTo( methodOn(AgentRestEndpoint.class).getAgent(entity.getPrimaryKey())).withSelfRel();
        entity.add(selfLink);
        
        Link propertiesLink = linkTo( 
                    methodOn(AgentRestEndpoint.class).getAgentProperties(entity.getPrimaryKey().toString(), null, null) 
                ).withRel( "properties" );
        
        entity.add(propertiesLink);

        if (entity.getProperties() != null) {
            entity.getProperties().forEach(p -> this.propertyResourceAssembler.toResource(p));
        }

        if (entity.getLeads() != null) {
            entity.getLeads().forEach(l -> this.leadResourceAssember.toResource(l));
        }

        return entity;
    }

}
