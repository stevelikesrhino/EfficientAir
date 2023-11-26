package com.ee542.iotgarbagecollector.controller;

import com.ee542.iotgarbagecollector.entity.Node;
import com.ee542.iotgarbagecollector.service.PredictService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PredictController {
    private final PredictService predictService;
    PredictController(PredictService predictService) {
        this.predictService = predictService;
    }

    @GetMapping("/predict")
    public List<Node> getDataBatch() {
        return predictService.getDataBatch();
    }
}
