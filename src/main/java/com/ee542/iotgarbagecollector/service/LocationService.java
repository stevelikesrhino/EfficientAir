package com.ee542.iotgarbagecollector.service;

import com.ee542.iotgarbagecollector.entity.Location;
import com.ee542.iotgarbagecollector.database.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {
    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<Location> getAllLocations(){
        return locationRepository.findAllItems();
    }
}
