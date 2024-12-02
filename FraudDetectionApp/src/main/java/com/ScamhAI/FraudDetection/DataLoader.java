package com.ScamhAI.FraudDetection;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {
    public List<Transaction> loadTransactions(String filePath) {
        List<Transaction> transactions = new ArrayList<>();
        try (Reader reader = new FileReader(filePath)) {
            @SuppressWarnings("deprecation")
			Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
            for (CSVRecord record : records) {
                Transaction transaction = new Transaction(
                        record.get("user_id"),
                        Double.parseDouble(record.get("amount")),
                        record.get("timestamp"),
                        Integer.parseInt(record.get("label"))
                );
                transactions.add(transaction);
            }
        } catch (Exception e) {
            System.err.println("Error loading transactions from CSV: " + e.getMessage());
            e.printStackTrace();
        }
        return transactions;
    }
}
