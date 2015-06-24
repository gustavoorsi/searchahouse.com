package edu.searchahouse.searchengine.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.searchahouse.searchengine.model.Location;
import edu.searchahouse.searchengine.persistence.repository.elasticsearch.LocationRepository;
import edu.searchahouse.searchengine.service.LocationService;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Autowired
    public LocationServiceImpl(final LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public List<Location> findLocationsByState(String autocompleteState) {

        List<Location> locations = this.locationRepository.findAutocompleteLocationState(autocompleteState);
        
        return locations;
    }

}
