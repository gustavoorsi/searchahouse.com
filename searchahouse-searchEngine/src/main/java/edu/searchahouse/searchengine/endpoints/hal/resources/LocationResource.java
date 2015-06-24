package edu.searchahouse.searchengine.endpoints.hal.resources;

import org.springframework.hateoas.Resource;

import edu.searchahouse.searchengine.model.Location;

public class LocationResource extends Resource<Location> {

    public LocationResource(final Location content) {
        super(content);
    }

}
