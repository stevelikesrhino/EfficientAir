package com.ee542.iotgarbagecollector.service;

import com.ee542.iotgarbagecollector.database.NodeRepository;
import com.ee542.iotgarbagecollector.entity.Node;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class PredictService {
    private final NodeService nodeService;
    private final NodeRepository nodeRepository;
    PredictService(NodeRepository nodeRepository,
                   NodeService nodeService) {
        this.nodeRepository = nodeRepository;
        this.nodeService = nodeService;
    }


    public List<Node> getDataBatch() {
        long cutoffTimestamp = Instant.now().minus(5, ChronoUnit.HOURS).toEpochMilli();
        return nodeRepository.getDataBatch(cutoffTimestamp);
    }

    private List<List<Node>> getCollectionBatches(List<Node> nodeList){
        List<List<Node>> ans = new ArrayList<>();
        List<Node> temp = new ArrayList<>();
        for(int i=0; i<nodeList.size()-1; i++){
            temp.add(nodeList.get(i));
            if(nodeList.get(i).fill() - nodeList.get(i+1).fill() > 15) {
                ans.add(temp);
                temp = new ArrayList<>();
            }
        }
        temp.add(nodeList.get(nodeList.size()-1));
        ans.add(temp);
        return ans;
    }

    public List<List<Node>> getCollectionBatches(){
        List<Node> nodeList = nodeService.getAllNodes();
        return getCollectionBatches(nodeList);
    }

    // TODO: Prediction function is only PLACEHOLDER
    private static Long nextFillUp(List<List<Node>> data) {
        // Get the most recent collection interval
        List<Node> currentInterval = data.get(data.size() - 1);

        // Check if there are enough data points in the current interval
        if (currentInterval.size() < 2) {
            return null; // Not enough data to make a prediction
        }

        // Perform linear regression on the current interval
        LinearRegression lr = new LinearRegression(currentInterval);

        // Find the time when the fill level is predicted to reach 100
        for (long time = currentInterval.get(currentInterval.size() - 1).time(); ; time += 60000) { // Checking every minute
            if (lr.predict(time) >= 100) {
                return time;
            }
        }
    }

    public Long nextFillUp(){
        List<List<Node>> data = this.getCollectionBatches();
        return nextFillUp(data);
    }
}
