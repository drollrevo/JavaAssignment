package com.mycompany.javaassignment.Class;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class Supplier extends JFrame {

    // Variable Initialize
    private String supplierID, supplierName, address, nation, supplierStatus, itemNo, itemName;
    private double price;
    private static final String SUPPLIER_FILE = System.getProperty("user.dir") + "/src/main/java/com/mycompany/javaassignment/Database/supplier.txt";
    CurrentTime time = new CurrentTime();

    // Constructors
    public Supplier() {
    }

    public Supplier(String supplierID, String supplierName, String itemNo, String itemName, String address, String nation, double price, String supplierStatus) {
        this.supplierID = supplierID;
        this.supplierName = supplierName;
        this.address = address;
        this.supplierStatus = supplierStatus;
        this.itemNo = itemNo;
        this.itemName = itemName;
        this.price = price;
        this.nation = nation;
    }

    // Getters
    public String getSupplierID() {
        return supplierID;
    }

    public String[] getSupplierID(String itemNo) {
        File supplierFile = new File(SUPPLIER_FILE);
        List<String> lines = new ArrayList<>(); // To hold all matching supplier IDs

        // Read the file content
        try (BufferedReader br = new BufferedReader(new FileReader(supplierFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 8 && parts[2].trim().equals(itemNo)) {
                    // Add the supplier ID (first part) to the list
                    lines.add(parts[0].trim());
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error processing supplier.txt.");
        }

        // Convert the list to a String array and return
        return lines.toArray(new String[0]);
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getAddress() {
        return address;
    }

    public String getNation() {
        return nation;
    }

    public String getSupplierStatus() {
        return supplierStatus;
    }

    public String getItemNo() {
        return itemNo;
    }

    public String getItemName() {
        return itemName;
    }

    public double getPrice() {
        return price;
    }

    // Setters
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public void setSupplierStatus(String supplierStatus) {
        this.supplierStatus = supplierStatus;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    /*Methods*/
    public String newSupID(String nation) {
        String line;
        String newSupID = "";
        String prefix = "SUP-" + nation + "-";
        int maxID = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(SUPPLIER_FILE))) {
            while ((line = br.readLine()) != null) {
                // Check if the line starts with the prefix
                String[] parts = line.split(",");

                if (parts[0].startsWith(prefix)) {
                    // Extract the last three digits and parse them as an integer
                    int currentID = Integer.parseInt(parts[0].substring(parts[0].length() - 3));
                    // Update maxID if the current ID is greater
                    if (currentID > maxID) {
                        maxID = currentID;
                    }
                }
            }
            // Generate the newSupID by incrementing the maxID
            newSupID = prefix + String.format("%03d", maxID + 1);
        } catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Fail to generate new SupplierID");
        }

        return newSupID;
    }

    public List<Supplier> supplierList() {
        List<Supplier> supplierList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(SUPPLIER_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split the line into variables
                String[] parts = line.split(",");
                if (parts.length == 8) { // Ensure correct number of fields
                    String supplierID = parts[0];
                    String supplierName = parts[1];
                    String itemNo = parts[2];
                    String itemName = parts[3];
                    String address = parts[4];
                    String nation = parts[5];
                    double price = Double.parseDouble(parts[6]);
                    String status = parts[7];

                    // Create a SalesEntry object and add it to the list
                    supplierList.add(new Supplier(supplierID, supplierName, itemNo, itemName, address, nation, price, status));
                }
            }

            br.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to Open supplier.txt.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid number format in supplier.txt.");
        }

        return supplierList;
    }

    public boolean createSupplier(String supplierId, String supplierName, String itemNo, String itemName, String address, String nation, double price, String status) {
        String currentDate = time.toDateTimeFormat();

        try {
            // Step 1: Read the entire file into memory
            List<String> fileContents = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader(SUPPLIER_FILE))) {
                String dataLine;
                while ((dataLine = br.readLine()) != null) {
                    fileContents.add(dataLine);
                    String[] data = dataLine.split(",");
                    // Check for duplicate supplier and itemNo
                    if (dataLine.trim().isEmpty()) {
                        continue; // Skip empty lines
                    }

                    if (data.length < 3) {
                        System.err.println("Skipping invalid line: " + dataLine);
                        continue;
                    }

                    // Check conditions safely
                    if (data[0].equalsIgnoreCase(supplierId)) {
                        if (!data[1].equalsIgnoreCase(supplierName)) {
                            JOptionPane.showMessageDialog(null, "Error: Supplier ID " + supplierId + " is for supplier: " + data[1]);
                            return false;
                        }
                        if (data[2].equalsIgnoreCase(itemNo)) {
                            JOptionPane.showMessageDialog(null, "Error: The item already exists for this supplier.");
                            return false;
                        }
                    } else if (data[1].equalsIgnoreCase(supplierName)) {
                        JOptionPane.showMessageDialog(null, "Error: Supplier " + supplierName + " is already assigned to a different ID: " + data[0]);
                        return false;
                    }
                }

                br.close();
            }

            String newRow = String.join(",", supplierId, supplierName, itemNo, itemName, address, nation, String.format("%.2f", price), status);
            fileContents.add(newRow);

            // Step 3: Write the updated content back to the file
            if (JOptionPane.showConfirmDialog(null, "Are you sure to add this supplier?", "Add Supplier Confirmation", JOptionPane.YES_NO_OPTION) == 0) {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(SUPPLIER_FILE))) {
                    for (String line : fileContents) {
                        bw.write(line);
                        bw.newLine();
                    }

                    JOptionPane.showMessageDialog(null, "Supplier added successfully.");
                    bw.close();
                    return true;
                }
            }
        } catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error adding new item: " + e.getMessage());
        }

        return false;
    }

    public void editSupplier(String supplierID, String supplierName, String itemNo, String itemName, String Address, String nation, String Price, String Status) {
        File supplierFile = new File(SUPPLIER_FILE);
        boolean entryFound = false;
        String updatedLine = null;
        List<String> lines = new ArrayList<>(); // To hold all lines

        // Read the file content
        try (BufferedReader br = new BufferedReader(new FileReader(supplierFile))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 8 && parts[0].trim().equals(supplierID)) {
                    // If the salesEntryNo matches, edit the line
                    entryFound = true;

                    updatedLine = String.join(",", new String[]{
                        supplierID, supplierName, itemNo, itemName, Address, nation, Price, Status
                    });

                    // Add the updated line to the list
                    lines.add(updatedLine);

                } else {
                    // Add the unchanged line to the list
                    lines.add(line);
                }
            }
            br.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error processing supplier.txt.");
            return;
        }

        // If entry was found, write all the lines back to the file
        if (entryFound) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(supplierFile))) {
                // Write all lines back to the file
                for (String line : lines) {
                    bw.write(line);
                    bw.newLine();
                }
                JOptionPane.showMessageDialog(null, "Supplier updated successfully.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error writing to suppliers.txt.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Supplier not found.");
        }
    }

    public void deleteSupplier(String supplierID, String itemNo) {
        File supplierFile = new File(SUPPLIER_FILE);
        boolean entryFound = false;
        StringBuilder updatedContent = new StringBuilder();

        // First, read the file and filter the content
        try (BufferedReader br = new BufferedReader(new FileReader(supplierFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 8 && parts[0].trim().equals(supplierID) && parts[2].trim().equals(itemNo)) {
                    // If the supplierID matches, mark it as found and log the deletion
                    entryFound = true;

                } else {
                    // Append non-matching lines to the updated content
                    updatedContent.append(line).append(System.lineSeparator());
                }
            }

            br.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading suppliers.txt.");
            return;
        }

        // Next, write the updated content back to the original file
        if (entryFound) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(supplierFile))) {
                bw.write(updatedContent.toString());
                bw.close();
                JOptionPane.showMessageDialog(null, "Supplier Deleted Successfully.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error writing to suppliers.txt.");
                return;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Supplier not found.");
        }
    }
}
