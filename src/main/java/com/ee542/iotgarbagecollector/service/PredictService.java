package com.ee542.iotgarbagecollector.service;

import com.ee542.iotgarbagecollector.database.NodeRepository;
import com.ee542.iotgarbagecollector.entity.Node;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class PredictService {
    private final NodeRepository nodeRepository;
    PredictService(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    public List<Node> getDataBatch() {
        long cutoffTimestamp = Instant.now().minus(5, ChronoUnit.HOURS).toEpochMilli();
        return nodeRepository.getDataBatch(cutoffTimestamp);
    }
}
