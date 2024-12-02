package com.ScamhAI.FraudDetection;

import java.util.ArrayList;
import java.util.List;

public class FeatureGenerator {
    public List<FeatureVector> generateFeatures(List<Transaction> transactions) {
        List<FeatureVector> featureVectors = new ArrayList<>();
        for (Transaction transaction : transactions) {
            FeatureVector vector = new FeatureVector();
            vector.addFeature("amount", transaction.getAmount());
            vector.addFeature("is_large_transaction", transaction.isLargeTransaction() ? 1.0 : 0.0);
            vector.addFeature("transaction_hour", (double) transaction.getTransactionHour());
            vector.addFeature("label", (double) transaction.getLabel());
            featureVectors.add(vector);
        }
        return featureVectors;
    }
}
