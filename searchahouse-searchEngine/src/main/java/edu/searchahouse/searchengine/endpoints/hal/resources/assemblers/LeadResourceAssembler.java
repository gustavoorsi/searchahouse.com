package edu.searchahouse.searchengine.endpoints.hal.resources.assemblers;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Component;

import edu.searchahouse.searchengine.model.Lead;

@Component
public class LeadResourceAssembler implements ResourceAssembler<Lead, ResourceSupport> {

    @Override
    public ResourceSupport toResource(Lead entity) {

        // add link to itself ( rel = self )
        // TODO: get the endpoint url that GET a property from micro-service CRUD. (using eureka). For now just hardcode the domain.
        // add link to itself ( rel = self )
        Link selfLink = new Link("http://localhost:8081/api/v1/property/" + entity.getPrimaryKey()).withRel("self");
        entity.add(selfLink);

        return entity;
    }

}
