package Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ScamhAI.FraudDetection.FeatureGenerator;
import com.ScamhAI.FraudDetection.Transaction;

import model.User;
import repository.UserRepository;

@RestController
@RequestMapping("/api")
public class APIController {
	
	@Autowired
    private UserRepository userRepository;

    // Sign Up: Save user credentials
    @PostMapping("/signup")
    public String signup(@RequestBody User user) {
        if (userRepository.existsById(user.getUsername())) {
            return "Username already exists!";
        }
        userRepository.save(user);
        return "User signed up successfully!";
    }

    // Login: Validate user credentials
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        User storedUser = userRepository.findByUsername(user.getUsername());
        if (storedUser != null && storedUser.getPassword().equals(user.getPassword())) {
            return "Login successful!";
        } else {
            return "Invalid credentials!";
        }
    }
    // Validate user for login
    @PostMapping("/validate")
    public ResponseEntity<String> validateUser(@RequestBody User user) {
        if ("admin".equals(user.getUsername()) && "password123".equals(user.getPassword())) {
            return ResponseEntity.ok("Validated");
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    // Handle file upload and fraud detection
    @PostMapping("/detect-fraud")
    public ResponseEntity<String> detectFraud(@RequestParam("file") MultipartFile file) {
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

    // Simulate loading transactions from a CSV file (implement CSV parsing)
    private List<Transaction> loadTransactions(String filePath) throws IOException {
        List<Transaction> transactions = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
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
