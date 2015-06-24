package edu.searchahouse.searchengine.service;

import java.util.List;

import edu.searchahouse.searchengine.model.Location;

public interface LocationService {

    List<Location> findLocationsByState(final String autocompleteState);

}
