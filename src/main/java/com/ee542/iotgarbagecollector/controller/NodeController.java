package com.ee542.iotgarbagecollector.controller;

import com.ee542.iotgarbagecollector.entity.Location;
import com.ee542.iotgarbagecollector.entity.Node;
import com.ee542.iotgarbagecollector.service.LocationService;
import com.ee542.iotgarbagecollector.service.NodeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class NodeController {
    private final NodeService service;

    public NodeController(NodeService service, LocationService locationService) {
        this.service = service;
    }

    @GetMapping("/nodes")
    public List<Node> getNodes() {
        return service.getAllNodes();
    }

    @GetMapping("/testinsert")
    public List<Node> testInsert() throws IOException {
        List<Node> nodeList = service.readDataFromFile();
        service.batchInsertNodes(nodeList);
        return nodeList;
    }
}