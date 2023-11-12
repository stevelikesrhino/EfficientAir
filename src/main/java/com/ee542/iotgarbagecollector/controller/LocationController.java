package com.ee542.iotgarbagecollector.controller;

import com.ee542.iotgarbagecollector.entity.Location;
import com.ee542.iotgarbagecollector.service.LocationService;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class LocationController {
    private final LocationService locationService;
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/locations")
    public List<Location> getLocations(){
        return locationService.getAllLocations();
    }
}

