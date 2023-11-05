package com.ee542.iotgarbagecollector.controller;

import com.ee542.iotgarbagecollector.database.Node;
import com.ee542.iotgarbagecollector.service.NodeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NodeController {
    private final NodeService service;
    public NodeController(NodeService service) {
        this.service = service;
    }

    @GetMapping("/nodes")
    public List<Node> getNodes() {
        return service.getAllNodes();
    }
}
