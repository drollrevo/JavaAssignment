
package com.mycompany.javaassignment;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SupplierEntry {
    private String action; // e.g., "Added", "Edited", "Deleted"
    private LocalDateTime timestamp; // Time of the action
    private Supplier supplier; // Reference to the affected Supplier
    private String userID; // User who performed the action
    
    // Formatter for the timestamp
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    // Constructor
    public SupplierEntry(String action, Supplier supplier, String userID) {
        this.action = action;
        this.timestamp = LocalDateTime.now(); // Automatically log the current time
        this.supplier = supplier;
        this.userID = userID;
    }

    // Display log details
    public void displayLog() {
        System.out.println("Action: " + action + ", Supplier: " + supplier.getSupplierName() +
                           " (" + supplier.getSupplierID() + "), Performed by: " + userID+
                           ", Timestamp: " + timestamp.format(FORMATTER));
    }
/*    public void displayLog() {
        System.out.println("Action: " + action);
        System.out.println("Timestamp: " + timestamp.format(FORMATTER));
        System.out.println("Supplier ID: " + supplier.getSupplierID());
        System.out.println("Supplier Name: " + supplier.getSupplierName());
        System.out.println("Performed by: " + userID);
    }*/
}
