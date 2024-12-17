package com.mycompany.javaassignment.Class;

import javax.swing.*;
import java.io.*;
import java.util.*;

public class SalesEntry extends JFrame {

    // Variable Initialize
    private String salesEntryNo, itemNo, itemName, date, userID;
    private int qty;
    private double pricePerUnit, amount;
//C:\Users\qianc\Documents\NetBeansProjects\JavaAssignment\JavaAssignment\src\main\java\com\mycompany\javaassignment\Database\salesEntry.txt
    private static final String SALES_FILENAME = System.getProperty("user.dir") + "\\src\\main\\java\\com\\mycompany\\javaassignment\\Database\\SalesEntry.txt";
    private static final String SALES_LOG_FILENAME = System.getProperty("user.dir") + "\\src\\main\\java\\com\\mycompany\\javaassignment\\Database\\SalesEntryLog.txt";

    // Constructors
    public SalesEntry() {
    }

    public SalesEntry(String salesEntryNo, String itemNo, String itemName, double pricePerUnit, int qty, double amount, String date, String userID) {
        this.salesEntryNo = salesEntryNo;
        this.itemNo = itemNo;
        this.itemName = itemName;
        this.date = date;
        this.userID = userID;
        this.qty = qty;
        this.pricePerUnit = pricePerUnit;
        this.amount = amount;
    }

    // Getters and Setters  
    public String getSalesEntryNo() {
        return salesEntryNo;
    }

    public void setSalesEntryNo(String salesEntryNo) {
        this.salesEntryNo = salesEntryNo;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    /*Methods*/
    public List<SalesEntry> salesEntryList() {
        List<SalesEntry> salesEntries = new ArrayList<>();

        System.out.println(SALES_FILENAME);
        
        try (BufferedReader br = new BufferedReader(new FileReader(SALES_FILENAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split the line into variables
                String[] parts = line.split(",");
                if (parts.length == 8) { // Ensure correct number of fields
                    String salesEntryNo = parts[0].trim();
                    String itemNo = parts[1].trim();
                    String itemName = parts[2].trim();
                    double pricePerUnit = Double.parseDouble(parts[3].trim());
                    int qty = Integer.parseInt(parts[4].trim());
                    double amount = Double.parseDouble(parts[5].trim());
                    String date = parts[6].trim();
                    String userID = parts[7].trim();

                    // Create a SalesEntry object and add it to the list
                    salesEntries.add(new SalesEntry(salesEntryNo, itemNo, itemName, pricePerUnit, qty, amount, date, userID));
                }
            }
            
            br.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to Open SalesEntry.txt.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid number format in SalesEntry.txt.");
        }

        return salesEntries;
    }

    public void createSalesEntry(String newRow) {
        String currentDate = new CurrentTime().toDateTimeFormat();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(SALES_FILENAME, true))) {
            bw.write(newRow);
            bw.newLine(); // Add a new line after the entry            

            bw.close();

            // Log the new sales entry creation with the date in the salesEntryLog.txt file
            try (BufferedWriter logbw = new BufferedWriter(new FileWriter(SALES_LOG_FILENAME, true))) {
                logbw.write("New SalesEntry Created: " + newRow + ", " + currentDate);
                logbw.newLine(); // Add a new line after the log entry      

                logbw.close();
            } catch (IOException logException) {
                JOptionPane.showMessageDialog(null, "Error writing to sales entry log file: " + logException.getMessage());
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error writing to sales entry file: " + e.getMessage());
        }
    }

    public void salesReport() {

    }

    public void editSalesEntry(String salesEntryNo, String itemNo, String itemName, String price, int qty, String amount, String date, String user) {
        File salesFile = new File(SALES_FILENAME);
        boolean entryFound = false;
        String updatedLine = null;
        List<String> lines = new ArrayList<>(); // To hold all lines

        // Read the file content
        try (BufferedReader br = new BufferedReader(new FileReader(salesFile))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 8 && parts[0].trim().equals(salesEntryNo)) {
                    // If the salesEntryNo matches, edit the line
                    entryFound = true;

                    // Create the updated line with new values
                    updatedLine = salesEntryNo + "," + itemNo + "," + itemName + "," + price + "," + qty + "," + amount + "," + date + "," + user;

                    // Add the updated line to the list
                    lines.add(updatedLine);

                    // Log the change before adding to the list
                    try (BufferedWriter logBw = new BufferedWriter(new FileWriter(SALES_LOG_FILENAME, true))) {
                        logBw.write("Edited SalesEntry: " + line + " -> " + updatedLine + " | Timestamp: " + new java.util.Date());
                        logBw.newLine();                        
                        logBw.close();
                    } catch (IOException logException) {
                        JOptionPane.showMessageDialog(null, "Error logging edited sales entry.");
                    }
                } else {
                    // Add the unchanged line to the list
                    lines.add(line);
                }
            }
            br.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error processing SalesEntry.txt.");
            return;
        }

        // If entry was found, write all the lines back to the file
        if (entryFound) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(salesFile))) {
                // Write all lines back to the file
                for (String line : lines) {
                    bw.write(line);
                    bw.newLine();
                }
                JOptionPane.showMessageDialog(null, "Sales entry updated successfully.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error writing to SalesEntry.txt.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Sales entry not found.");
        }
    }

    public void deleteSalesEntry(String salesEntryNo) {
        File salesFile = new File(SALES_FILENAME);
        boolean entryFound = false;
        StringBuilder updatedContent = new StringBuilder();

        // First, read the file and filter the content
        try (BufferedReader br = new BufferedReader(new FileReader(salesFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 8 && parts[0].trim().equals(salesEntryNo)) {
                    // If the salesEntryNo matches, mark it as found and log the deletion
                    entryFound = true;

                    try (BufferedWriter logBw = new BufferedWriter(new FileWriter(SALES_LOG_FILENAME, true))) {
                        logBw.write("Deleted Entry: " + line + " | Timestamp: " + new java.util.Date());
                        logBw.newLine();

                        logBw.close();
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(null, "Error logging deleted sales entry.");
                    }
                } else {
                    // Append non-matching lines to the updated content
                    updatedContent.append(line).append(System.lineSeparator());
                }
            }

            br.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading SalesEntry.txt.");
            return;
        }

        // Next, write the updated content back to the original file
        if (entryFound) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(salesFile))) {
                bw.write(updatedContent.toString());
                bw.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error writing to SalesEntry.txt.");
                return;
            }
            JOptionPane.showMessageDialog(null, "Sales entry deleted successfully.");
        } else {
            JOptionPane.showMessageDialog(null, "Sales entry not found.");
        }
    }

    public int rowCount() {
        File file = new File(SALES_FILENAME);

        if (!file.exists() || !file.isFile()) {
            JOptionPane.showMessageDialog(null, String.format("File not found or is not a valid file: " + SALES_FILENAME));
            return 0; // Return 0 if the file doesn't exist
        }

        // Get today's date in YYYYMMDD format
        CurrentTime time = new CurrentTime(); // Assuming you have a CurrentTime class

        // Regular expression to match the format ST-YYYYMMDDXXX
        String formatRegex = "^ST-" + time.getDateFormat() + "\\d{3}$";

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

    @Override
    public String toString() {
        return "SalesEntry{" + "salesEntryNo=" + salesEntryNo + ", itemNo=" + itemNo + ", itemName=" + itemName + ", date=" + date + ", userID=" + userID + ", qty=" + qty + ", pricePerUnit=" + pricePerUnit + ", amount=" + amount + '}';
    }
}
