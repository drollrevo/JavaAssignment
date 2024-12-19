package com.mycompany.javaassignment.Class;

import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

public class Payment {

    private String paymentNo;
    private String poNo;
    private double amount;
    private String itemNo;
    private String supplierID;
    private String paymentDate;
    private String paymentStatus;

    private final String PAYMENT_FILENAME = System.getProperty("user.dir") + "\\src\\main\\java\\com\\mycompany\\javaassignment\\Database\\payment.txt";

    CurrentTime time = new CurrentTime();
    
    //Constructor
    public Payment() {

    }

    public Payment(String poNo, String itemNo, String supplierID, double amount, String paymentDate, String paymentStatus) {
        this.poNo = poNo;
        this.amount = amount;
        this.itemNo = itemNo;
        this.supplierID = supplierID;
        this.paymentDate = paymentDate;
        this.paymentStatus = paymentStatus;
    }

    //Getter
    public String getPaymentNo() {
        return paymentNo;
    }

    public String getPoNo() {
        return poNo;
    }

    public double getAmount() {
        return amount;
    }

    public String getItemNo() {
        return itemNo;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public String getPaymentDate(String poNo) {
        this.paymentDate = null; // Initialize itemName as null

        try {
            FileReader fr = new FileReader(PAYMENT_FILENAME);
            BufferedReader br = new BufferedReader(fr);

            String line;

            br.readLine();
            // Read each line from the file            
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(","); // Assuming CSV format: itemNo,itemName,...

                String currentPONo = parts[0].trim();
                String currentPaymentDate = parts[5].trim();
                
                // Check if the itemNo matches the current line's itemNo and itemStatus not inactive
                if (currentPONo.equals(poNo)) {
                    paymentDate = currentPaymentDate; // Set the itemName if itemNo matches
                    break; // No need to continue searching after finding the item
                }
            }

            fr.close();
            br.close();

        } catch (IOException e) {
            System.out.println("item.txt not found.");
        }

        return paymentDate; // Will return null if itemNo not found
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    //Setter
    public void setPaymentNo(String paymentNo) {
        this.paymentNo = paymentNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    // Methods for managing payment records
    public List<Payment> paymentList() {
        List<Payment> payment = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(PAYMENT_FILENAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }

                // Split the line into parts using comma delimiter
                String[] parts = line.split(",");

                // Handle the case where the line has fewer than 13 parts
                if (parts.length != 6) {
                    // You could either continue skipping invalid lines or fill in default values for missing fields
                    // For example, you can fill the missing fields with default values:
                    while (parts.length < 6) {
                        parts = Arrays.copyOf(parts, parts.length + 1);
                        parts[parts.length - 1] = "-";  // Fill with placeholder
                    }

                    // Proceed with processing the line after filling missing fields
                }

                // Proceed to parse the values if the line has exactly 13 parts
                String poNo = parts[0].trim();
                String itemNo = parts[1].trim();
                String supplier = parts[2].trim();
                double amount = Double.parseDouble(parts[3].trim());
                String paymentDate = parts[4].trim();
                String paymentStatus = parts[5].trim();

                // Create Payment object and add it to the list
                payment.add(new Payment(poNo, itemNo, supplier, amount, paymentDate, paymentStatus));
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to Open payment.txt.");
        }

        return payment;
    }

    public void addPayment(String newPayment) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PAYMENT_FILENAME, true))) {
            bw.write(newPayment + ", " + time.toDateFormat());
            JOptionPane.showMessageDialog(null, "You have paid successfully.");                                    
            
            bw.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error to open payment.txt");
        }
    }

    public void paymentHistory() {
    }
}
