package com.ee542.iotgarbagecollector.service;

import com.ee542.iotgarbagecollector.entity.Node;
import com.ee542.iotgarbagecollector.database.NodeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NodeService {
    private final NodeRepository nodeRepository;
    public NodeService(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }
    public List<Node> getAllNodes() {
        return nodeRepository.findAllItems();
    }
}
