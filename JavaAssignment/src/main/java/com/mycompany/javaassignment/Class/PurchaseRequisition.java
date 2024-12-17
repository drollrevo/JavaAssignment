package com.mycompany.javaassignment.Class;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class PurchaseRequisition {

    private String prNo, itemNo, itemName, supplier, reason, description, dateRequested, dateIssued, userRequested, personInCharge, status;
    private int purchaseQty;

    private static final String PR_FILENAME = System.getProperty("user.dir") + "\\src\\main\\java\\com\\mycompany\\javaassignment\\Database\\pr.txt";
    private static final String PR_LOG_FILENAME = System.getProperty("user.dir") + "\\src\\main\\java\\com\\mycompany\\javaassignment\\Database\\prLog.txt";

    /*
    PRNo, itemNo, itemName, supplierID, reason, description, dateRequest, dateIssued, userRequest, progressStatus
    PR-20241116-0001, PROD-10001, Television, SUP-2024-KUL-001, out of stock, 1-12-2024, 16-11-2024, -, SM-1001, Reviewing
     */
    public PurchaseRequisition() {
    }

    public PurchaseRequisition(String prNo, String itemNo, String itemName, String supplier, int purchaseQty, String reason, String description, String dateRequested, String dateIssued, String userRequested, String personInCharge, String status) {
        this.prNo = prNo;
        this.itemNo = itemNo;
        this.itemName = itemName;
        this.reason = reason;
        this.description = description;
        this.dateRequested = dateRequested;
        this.dateIssued = dateIssued;
        this.userRequested = userRequested;
        this.status = status;
        this.purchaseQty = purchaseQty;
        this.personInCharge = personInCharge;
        this.supplier = supplier;
    }

    /*Getters and Setters*/
    public String getPrNo() {
        return prNo;
    }

    public void setPrNo(String prNo) {
        this.prNo = prNo;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateRequested() {
        return dateRequested;
    }

    public void setDateRequested(String dateRequested) {
        this.dateRequested = dateRequested;
    }

    public String getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(String dateIssued) {
        this.dateIssued = dateIssued;
    }

    public String getUserRequested() {
        return userRequested;
    }

    public void setUserRequested(String userRequested) {
        this.userRequested = userRequested;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPurchaseQty() {
        return purchaseQty;
    }

    public void setPurchaseQty(int purchaseQty) {
        this.purchaseQty = purchaseQty;
    }

    public String getPersonInCharge() {
        return personInCharge;
    }

    public void setPersonInCharge(String personInCharge) {
        this.personInCharge = personInCharge;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    /*Methods*/
    public List<PurchaseRequisition> prList() {
        List<PurchaseRequisition> pr = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(PR_FILENAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Skip empty lines
                if (line.trim().isEmpty()) {
                    continue;
                }

                // Split the line into parts using comma delimiter
                String[] parts = line.split(",");

                // Handle the case where the line has fewer than 13 parts
                if (parts.length != 13) {
                    // You could either continue skipping invalid lines or fill in default values for missing fields
                    // For example, you can fill the missing fields with default values:
                    while (parts.length < 13) {
                        parts = Arrays.copyOf(parts, parts.length + 1);
                        parts[parts.length - 1] = "-";  // Fill with placeholder
                    }

                    // Proceed with processing the line after filling missing fields
                }

                // Proceed to parse the values if the line has exactly 13 parts
                String prNo = parts[0].trim();
                String itemNo = parts[1].trim();
                String itemName = parts[2].trim();
                String supplier = parts[3].trim();

                // Add error handling for integer parsing
                int qty = 0;
                try {
                    qty = Integer.parseInt(parts[4].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid quantity in line: " + line);
                    continue; // Skip this line if the quantity is invalid
                }

                // Parsing other fields
                String reason = parts[5].trim();
                String description = parts[6].trim();
                String dateRequested = parts[7].trim();
                String dateIssued = parts[8].trim();
                String userRequested = parts[9].trim();
                String personInCharge = parts[10].trim();
                String status = parts[11].trim();

                // Create PurchaseRequisition object and add it to the list
                pr.add(new PurchaseRequisition(prNo, itemNo, itemName, supplier, qty, reason, description, dateRequested, dateIssued, userRequested, personInCharge, status));
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to Open SalesEntry.txt.");
        }

        return pr;
    }

    public void createPR(String newRow) {
        String currentDate = new CurrentTime().toDateTimeFormat();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PR_FILENAME, true))) {
            bw.write(newRow);
            bw.newLine(); // Add a new line after the entry            

            bw.close();

            // Log the new sales entry creation with the date in the salesEntryLog.txt file
            try (BufferedWriter logbw = new BufferedWriter(new FileWriter(PR_LOG_FILENAME, true))) {
                logbw.write("New PR Created: " + newRow + ", " + currentDate);
                logbw.newLine(); // Add a new line after the log entry      

                logbw.close();
            } catch (IOException logException) {
                JOptionPane.showMessageDialog(null, "Error writing to pr log file: " + logException.getMessage());
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error writing to pr file: " + e.getMessage());
        }
    }

    public void editPR(String prNo, String itemNo, String itemName, String supplierID, int qty, String reason, String description, String dateRequested, String dateIssued, String userRequested, String personInCharge, String progressStatus) {
        File prFile = new File(PR_FILENAME);
        boolean entryFound = false;
        String updatedLine = null;
        List<String> lines = new ArrayList<>(); // To hold all lines

        // Read the file content
        try (BufferedReader br = new BufferedReader(new FileReader(prFile))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 12 && parts[0].trim().equals(prNo)) {
                    // If the prNo matches, edit the line
                    entryFound = true;

                    // Create the updated line with new values
                    updatedLine = prNo + "," + itemNo + "," + itemName + "," + supplierID + "," + qty + "," + reason + "," + description + "," + dateRequested + "," + dateIssued + "," + userRequested + "," + personInCharge + "," + progressStatus;

                    // Add the updated line to the list
                    lines.add(updatedLine);

                    // Log the change before adding to the list
                    try (BufferedWriter logBw = new BufferedWriter(new FileWriter(PR_LOG_FILENAME, true))) {
                        logBw.write("Edited PR: " + line + " -> " + updatedLine + " | Timestamp: " + new java.util.Date());
                        logBw.newLine();
                        logBw.close();
                    } catch (IOException logException) {
                        JOptionPane.showMessageDialog(null, "Error logging edited purchase requisition.");
                    }
                } else {
                    // Add the unchanged line to the list
                    lines.add(line);
                }
            }
            br.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error processing PurchaseRequisition.txt.");
            return;
        }

        // If entry was found, write all the lines back to the file
        if (entryFound) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(prFile))) {
                // Write all lines back to the file
                for (String line : lines) {
                    bw.write(line);
                    bw.newLine();
                }
                JOptionPane.showMessageDialog(null, "Purchase requisition updated successfully.");
                
                bw.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error writing to PurchaseRequisition.txt.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Purchase requisition not found.");
        }
    }

    public void deletePR(String prNo) {
        File prFile = new File(PR_FILENAME);
        boolean entryFound = false;
        StringBuilder updatedContent = new StringBuilder();

        // First, read the file and filter the content
        try (BufferedReader br = new BufferedReader(new FileReader(prFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 12 && parts[0].trim().equals(prNo)) {
                    // If the prNo matches, mark it as found and log the deletion
                    entryFound = true;

                    try (BufferedWriter logBw = new BufferedWriter(new FileWriter(PR_LOG_FILENAME, true))) {
                        logBw.write("Deleted PR: " + line + " | Timestamp: " + new java.util.Date());
                        logBw.newLine();
                        logBw.close();
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(null, "Error logging deleted purchase requisition.");
                    }
                } else {
                    // Append non-matching lines to the updated content
                    updatedContent.append(line).append(System.lineSeparator());
                }
            }
            br.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading PurchaseRequisition.txt.");
            return;
        }

        // Next, write the updated content back to the original file
        if (entryFound) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(prFile))) {
                bw.write(updatedContent.toString());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error writing to PurchaseRequisition.txt.");
                return;
            }
            JOptionPane.showMessageDialog(null, "Purchase requisition deleted successfully.");
        } else {
            JOptionPane.showMessageDialog(null, "Purchase requisition not found.");
        }
    }

    public int rowCount() {
        File file = new File(PR_FILENAME);

        if (!file.exists() || !file.isFile()) {
            JOptionPane.showMessageDialog(null, String.format("File not found or is not a valid file: " + PR_FILENAME));
            return 0; // Return 0 if the file doesn't exist
        }

        // Get today's date in YYYYMMDD format
        CurrentTime time = new CurrentTime(); // Assuming you have a CurrentTime class

        // Regular expression to match the format ST-YYYYMMDDXXX
        String formatRegex = "^PR-" + time.getDateFormat() + "\\d{4}$";

        int count = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split("\\s*,\\s*"); // Assuming columns are comma-separated
                if (columns.length > 0 && columns[0].matches(formatRegex)) {
                    count++;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }

        return count;
    }
}
