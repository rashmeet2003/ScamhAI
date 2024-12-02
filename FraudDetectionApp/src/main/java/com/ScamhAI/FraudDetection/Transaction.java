package com.ScamhAI.FraudDetection;

public class Transaction {
    private String userId;
    private double amount;
    private String timestamp;
    private int label;

    public Transaction(String userId, double amount, String timestamp, int label) {
        this.userId = userId;
        this.amount = amount;
        this.timestamp = timestamp;
        this.label = label;
    }

    public String getUserId() {
        return userId;
    }

    public double getAmount() {
        return amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public int getLabel() {
        return label;
    }

    public boolean isLargeTransaction() {
        return amount > 1000.0;
    }

    public int getTransactionHour() {
        String[] timeParts = timestamp.split("T")[1].split(":");
        return Integer.parseInt(timeParts[0]);
    }
}
