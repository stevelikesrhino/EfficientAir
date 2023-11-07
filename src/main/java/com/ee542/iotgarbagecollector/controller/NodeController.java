package com.ee542.iotgarbagecollector.controller;

import com.ee542.iotgarbagecollector.database.Location;
import com.ee542.iotgarbagecollector.database.Node;
import com.ee542.iotgarbagecollector.service.LocationService;
import com.ee542.iotgarbagecollector.service.NodeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NodeController {
    private final NodeService service;
    private final LocationService locationService;
    public NodeController(NodeService service, LocationService locationService) {
        this.service = service;
        this.locationService = locationService;
    }

    @GetMapping("/nodes")
    public List<Node> getNodes() {
        return service.getAllNodes();
    }

    @GetMapping("/locations")
    public List<Location> getLocations(){
        return locationService.getAllLocations();
    }
}
