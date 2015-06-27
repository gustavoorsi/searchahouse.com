package edu.searchahouse.endpoints.resources.assemblers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Component;

import edu.searchahouse.endpoints.AgentRestEndpoint;
import edu.searchahouse.model.Agent;

@Component
public class AgentResourceAssembler implements ResourceAssembler<Agent, ResourceSupport> {

    private final PropertyResourceAssembler propertyResourceAssembler;
    private final LeadResourceAssembler leadResourceAssembler;

    @Autowired
    public AgentResourceAssembler(final PropertyResourceAssembler propertyResourceAssembler, final LeadResourceAssembler leadResourceAssembler) {
        this.propertyResourceAssembler = propertyResourceAssembler;
        this.leadResourceAssembler = leadResourceAssembler;
    }

    @Override
    public ResourceSupport toResource(Agent entity) {

        // add link to itself ( rel = self )
        Link selfLink = linkTo(methodOn(AgentRestEndpoint.class).getAgent(entity.getPrimaryKey().toString(), false)).withSelfRel();
        entity.add(selfLink);
        
        if( entity.getProperties() != null ){
            entity.getProperties().forEach(p -> this.propertyResourceAssembler.toResource(p));
        }

        if( entity.getLeads() != null ){
            entity.getLeads().forEach( l -> this.leadResourceAssembler.toResource(l) );
        }

        return entity;
    }

}
