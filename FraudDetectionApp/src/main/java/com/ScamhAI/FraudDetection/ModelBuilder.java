package com.ScamhAI.FraudDetection;

import java.util.List;

public class ModelBuilder {
    private boolean modelBuilt = false;

    public void buildModel(List<FeatureVector> processedTransactions) {
        modelBuilt = true;
        System.out.println("Model built with " + processedTransactions.size() + " feature vectors!");
    }

    public String classifyTransaction(FeatureVector vector) {
        if (!modelBuilt) {
            throw new IllegalStateException("Model has not been built yet!");
        }

        // Simple heuristic for classification
        double amount = vector.getFeatures().get(0); // First feature: amount
        return amount > 1000.0 ? "1" : "0";
    }
}
