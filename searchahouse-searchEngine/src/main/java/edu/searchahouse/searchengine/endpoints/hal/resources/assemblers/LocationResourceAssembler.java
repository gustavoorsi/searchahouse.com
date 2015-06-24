package edu.searchahouse.searchengine.endpoints.hal.resources.assemblers;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import edu.searchahouse.searchengine.endpoints.hal.resources.LocationResource;
import edu.searchahouse.searchengine.model.Location;

@Component
public class LocationResourceAssembler implements ResourceAssembler<Location, LocationResource> {

    @Override
    public LocationResource toResource(Location entity) {

        LocationResource lr = new LocationResource(entity);

        Link selfLink = new Link("http://localhost:8081/api/v1/location/" + entity.getId()).withRel("self");
        lr.add(selfLink);

        return lr;
    }

}
