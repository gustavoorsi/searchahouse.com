package edu.searchahouse.searchengine.endpoints.hal.resources.assemblers;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Component;

import edu.searchahouse.searchengine.model.Location;

@Component
public class LocationResourceAssembler implements ResourceAssembler<Location, ResourceSupport> {

    @Override
    public ResourceSupport toResource(Location entity) {

        Link selfLink = new Link("http://localhost:8081/api/v1/location/" + entity.getPrimaryKey()).withRel("self");
        entity.add(selfLink);

        return entity;
    }

}
