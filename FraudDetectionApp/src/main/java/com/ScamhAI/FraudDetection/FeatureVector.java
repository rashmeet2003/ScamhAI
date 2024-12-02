package com.ScamhAI.FraudDetection;

import java.util.ArrayList;
import java.util.List;

public class FeatureVector {  // Make it public
    private final List<Double> features;

    public FeatureVector() {
        features = new ArrayList<>();
    }

    public void addFeature(String featureName, double value) {
        features.add(value);
    }

    public List<Double> getFeatures() {
        return features;
    }
}
