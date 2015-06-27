package edu.searchahouse.endpoints.resources.assemblers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Component;

import edu.searchahouse.endpoints.LeadRestEndpoint;
import edu.searchahouse.model.Lead;

@Component
public class LeadResourceAssembler implements ResourceAssembler<Lead, ResourceSupport> {

    @Override
    public ResourceSupport toResource(Lead entity) {

        // add link to itself ( rel = self )
        Link selfLink = linkTo(methodOn(LeadRestEndpoint.class).getLead(entity.getPrimaryKey().toString())).withSelfRel();
        entity.add(selfLink);

        return entity;
    }

}
