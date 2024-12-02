package Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import com.ScamhAI.FraudDetection.FeatureGenerator;
import com.ScamhAI.FraudDetection.Transaction;
import org.apache.catalina.User;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class APIController {

    @PostMapping("/validate")
    public ResponseEntity<String> validateUser(@RequestBody User user) {
        if ("admin".equals(user.getUsername()) && "password123".equals(user.getPassword())) {
            return ResponseEntity.ok("Validated");
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    // Updated to accept a MultipartFile for file upload
    @PostMapping("/detect-fraud")
    public ResponseEntity<String> detectFraud(@RequestParam("file") MultipartFile file) {
        // Process the uploaded file and detect fraud
        try {
            // Save the uploaded file temporarily for further processing
            File tempFile = File.createTempFile("transactions", ".csv");
            file.transferTo(tempFile);
            
            List<Transaction> transactions = loadTransactions(tempFile.getAbsolutePath());  // Load transactions from the file path
            
            FeatureGenerator generator = new FeatureGenerator();
            List<com.ScamhAI.FraudDetection.FeatureVector> features = generator.generateFeatures(transactions);
            
            // Placeholder for fraud detection logic using the generated features
            boolean fraudDetected = detectFraudLogic(features);
            
            if (fraudDetected) {
                return ResponseEntity.ok("Fraud detected in transactions.");
            } else {
                return ResponseEntity.ok("No fraud detected.");
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error processing file: " + e.getMessage());
        }
    }

    // This method simulates loading transactions from a CSV file (you need to implement CSV parsing logic)
    private List<Transaction> loadTransactions(String filePath) throws IOException {
        List<Transaction> transactions = new ArrayList<>();
        
        // Assuming you are using Apache Commons CSV to parse the CSV file
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(","); // Assuming CSV is comma-separated
                if (data.length == 4) {
                    String userId = data[0];
                    double amount = Double.parseDouble(data[1]);
                    String timestamp = data[2];
                    int label = Integer.parseInt(data[3]);
                    
                    Transaction transaction = new Transaction(userId, amount, timestamp, label);
                    transactions.add(transaction);
                }
            }
        }
        return transactions;
    }

    // Simulate fraud detection logic (you can implement actual fraud detection logic here)
    private boolean detectFraudLogic(List<com.ScamhAI.FraudDetection.FeatureVector> features) {
        // Placeholder logic for fraud detection based on features
        // For now, always return false (no fraud detected)
        return false;
    }
}
