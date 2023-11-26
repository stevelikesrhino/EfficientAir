package com.ee542.iotgarbagecollector.service;

import com.ee542.iotgarbagecollector.entity.Node;

import java.util.List;

public class LinearRegression {
    private double slope;
    private double intercept;

    public LinearRegression(List<Node> nodes) {
        double sumX = 0, sumY = 0, sumX2 = 0, sumXY = 0;
        int n = nodes.size();

        for (Node node : nodes) {
            sumX += node.time();
            sumY += node.fill();
            sumX2 += Math.pow(node.time(), 2);
            sumXY += node.time() * node.fill();
        }

        double xMean = sumX / n;
        double yMean = sumY / n;

        this.slope = (n * sumXY - sumX * sumY) / (n * sumX2 - Math.pow(sumX, 2));
        this.intercept = yMean - this.slope * xMean;
    }

    public double predict(double x) {
        return this.slope * x + this.intercept;
    }
}

