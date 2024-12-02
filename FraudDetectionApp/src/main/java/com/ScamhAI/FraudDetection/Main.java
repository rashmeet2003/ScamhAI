package com.ScamhAI.FraudDetection;

import javax.swing.*;
import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Step 1: Open file dialog to upload CSV file
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select CSV File");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("CSV Files", "csv"));
        int userSelection = fileChooser.showOpenDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            System.out.println("File selected: " + file.getAbsolutePath());

            try {
                // Step 2: Load Data from the selected file
                DataLoader dataLoader = new DataLoader();
                List<Transaction> transactions = dataLoader.loadTransactions(file.getAbsolutePath());

                // Step 3: Feature Generation
                FeatureGenerator featureGenerator = new FeatureGenerator();
                List<FeatureVector> processedTransactions = featureGenerator.generateFeatures(transactions);

                // Step 4: Build Model
                ModelBuilder modelBuilder = new ModelBuilder();
                modelBuilder.buildModel(processedTransactions);

                System.out.println("Model built successfully!");

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No file selected!");
        }
    }
}
