package com.mycompany.javaassignment.Class;

import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

public class PurchaseOrder {

    private String poNo;
    private String itemNo;
    private String itemName;
    private String supplier;
    private int qty;
    private double unitPrice;
    private double totalPrice;
    private String estReceiveDate;
    private String dateRequest;
    private String dateApproved;
    private String userRequested;
    private String pic;
    private String status;

    private final String PO_FILENAME = System.getProperty("user.dir") + "\\src\\main\\java\\com\\mycompany\\javaassignment\\Database\\purchase_orders.txt";
    private final String PO_LOG_FILENAME = System.getProperty("user.dir") + "\\src\\main\\java\\com\\mycompany\\javaassignment\\Database\\purchase_orders_log.txt";

    CurrentTime time = new CurrentTime();

    // Constructor to initialize PurchaseOrder
    public PurchaseOrder() {
    }

    public PurchaseOrder(String poNo, String itemNo, String itemName, String supplier, int qty, double unitPrice, double totalPrice, String estReceiveDate, String dateRequest, String dateApproved, String userRequested, String pic, String status) {
        this.poNo = poNo;
        this.itemNo = itemNo;
        this.itemName = itemName;
        this.supplier = supplier;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.estReceiveDate = estReceiveDate;
        this.dateRequest = dateRequest;
        this.dateApproved = dateApproved;
        this.userRequested = userRequested;
        this.pic = pic;
        this.status = status;
    }

    // Getter methods
    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
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

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getEstReceiveDate() {
        return estReceiveDate;
    }

    public void setEstReceiveDate(String estReceiveDate) {
        this.estReceiveDate = estReceiveDate;
    }

    public String getDateRequest() {
        return dateRequest;
    }

    public void setDateRequest(String dateRequest) {
        this.dateRequest = dateRequest;
    }

    public String getDateApproved() {
        return dateApproved;
    }

    public void setDateApproved(String dateApproved) {
        this.dateApproved = dateApproved;
    }

    public String getUserRequested() {
        return userRequested;
    }

    public void setUserRequested(String userRequested) {
        this.userRequested = userRequested;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CurrentTime getTime() {
        return time;
    }

    public void setTime(CurrentTime time) {
        this.time = time;
    }

    // Method to view all Purchase Orders
    public List<PurchaseOrder> poList() {
        List<PurchaseOrder> poEntries = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(PO_FILENAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split the line into variables
                String[] parts = line.split(",");
                if (parts.length == 13) { // Ensure correct number of fields
                    String poNo = parts[0].trim();
                    String itemNo = parts[1].trim();
                    String itemName = parts[2].trim();
                    String supplier = parts[3].trim();
                    int qty = Integer.parseInt(parts[4].trim());
                    double unitPrice = Double.parseDouble(parts[5].trim());
                    double totalPrice = Double.parseDouble(parts[6].trim());
                    String estReceiveDate = parts[7].trim();
                    String dateRequest = parts[8].trim();
                    String dateApproved = parts[9].trim();
                    String userRequest = parts[10].trim();
                    String pic = parts[11].trim();
                    String status = parts[12].trim();

                    // Create a PurchaseOrder object and add it to the list
                    poEntries.add(new PurchaseOrder(poNo, itemNo, itemName, supplier, qty, unitPrice, totalPrice, estReceiveDate, dateRequest, dateApproved, userRequest, pic, status));
                }
            }

            br.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to Open PurchaseOrder.txt.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid number format in PurchaseOrder.txt.");
        }

        return poEntries;
    }

    // Method to add a new Purchase Order
    public void addPurchaseOrder(String newRow) {
        String currentDate = time.toDateTimeFormat();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PO_FILENAME, true))) {
            bw.write(newRow);
            bw.newLine(); // Add a new line after the entry            

            bw.close();

            // Log the new sales entry creation with the date in the salesEntryLog.txt file
            try (BufferedWriter logbw = new BufferedWriter(new FileWriter(PO_LOG_FILENAME, true))) {
                logbw.write("New Purchase Order Generated: " + newRow + ", " + currentDate);
                logbw.newLine(); // Add a new line after the log entry      

                JOptionPane.showMessageDialog(null, "PO Generated Successfully.");

                logbw.close();
            } catch (IOException logException) {
                JOptionPane.showMessageDialog(null, "Error writing to purchase order log file: " + logException.getMessage());
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error writing to purchase order file: " + e.getMessage());
        }
    }

    public void updateStatus(String poNo, String status) {
        File poFile = new File(PO_FILENAME);
        boolean entryFound = false;
        String updatedLine = null;
        List<String> lines = new ArrayList<>(); // To hold all lines

        // Read the file content
        try (BufferedReader br = new BufferedReader(new FileReader(poFile))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 13 && parts[0].trim().equals(poNo)) {
                    // If the poNo matches, edit the line
                    entryFound = true;

                    // Create the updated line with new values
                    updatedLine = String.join(", ",
                            new String[]{poNo, parts[1].trim(), parts[2].trim(), parts[3].trim(),
                                parts[4].trim(),parts[5].trim(),parts[6].trim(),parts[7].trim(), 
                                parts[8].trim(), parts[9].trim(), parts[10].trim(), parts[11].trim(), status});

                    // Add the updated line to the list
                    lines.add(updatedLine);

                    // Log the change before adding to the list
                    try (BufferedWriter logBw = new BufferedWriter(new FileWriter(PO_LOG_FILENAME, true))) {
                        logBw.write("Edited PO: " + line + " -> " + updatedLine + " | Timestamp: " + time.toDateTimeFormat());
                        logBw.newLine();
                        logBw.close();
                    } catch (IOException logException) {
                        JOptionPane.showMessageDialog(null, "Error logging edited purchase order.");
                    }
                } else {
                    // Add the unchanged line to the list
                    lines.add(line);
                }
            }
            br.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error processing PurchaseOrder.txt.");
            return;
        }

        // If entry was found, write all the lines back to the file
        if (entryFound) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(poFile))) {
                // Write all lines back to the file
                for (String line : lines) {
                    bw.write(line);
                    bw.newLine();
                }
                JOptionPane.showMessageDialog(null, "Purchase order updated successfully.");

                bw.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error writing to PurchaseOrder.txt.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Purchase order not found.");
        }
    }

    // Method to edit an existing Purchase Order's status and other fields
    public void editPurchaseOrder(String poNo, String itemNo, String itemName, String supplier, int qty, double unitPrice, double totalPrice, String estReceiveDate, String dateRequested, String dateApproved, String userRequested, String pic, String status) {
        File poFile = new File(PO_FILENAME);
        boolean entryFound = false;
        String updatedLine = null;
        List<String> lines = new ArrayList<>(); // To hold all lines

        // Read the file content
        try (BufferedReader br = new BufferedReader(new FileReader(poFile))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 13 && parts[0].trim().equals(poNo)) {
                    // If the poNo matches, edit the line
                    entryFound = true;

                    // Create the updated line with new values
                    updatedLine = String.join(", ",
                            new String[]{poNo, itemNo, itemName, supplier,
                                String.format("%d", qty),
                                String.format("%.2f", unitPrice),
                                String.format("%.2f", totalPrice),
                                estReceiveDate, dateRequested, dateApproved, userRequested, pic, status});

                    // Add the updated line to the list
                    lines.add(updatedLine);

                    // Log the change before adding to the list
                    try (BufferedWriter logBw = new BufferedWriter(new FileWriter(PO_LOG_FILENAME, true))) {
                        logBw.write("Edited PO: " + line + " -> " + updatedLine + " | Timestamp: " + time.toDateTimeFormat());
                        logBw.newLine();
                        logBw.close();
                    } catch (IOException logException) {
                        JOptionPane.showMessageDialog(null, "Error logging edited purchase order.");
                    }
                } else {
                    // Add the unchanged line to the list
                    lines.add(line);
                }
            }
            br.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error processing PurchaseOrder.txt.");
            return;
        }

        // If entry was found, write all the lines back to the file
        if (entryFound) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(poFile))) {
                // Write all lines back to the file
                for (String line : lines) {
                    bw.write(line);
                    bw.newLine();
                }
                JOptionPane.showMessageDialog(null, "Purchase order updated successfully.");

                bw.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error writing to PurchaseOrder.txt.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Purchase order not found.");
        }
    }

    // Method to delete a Purchase Order by poNo
    public void deletePurchaseOrder(String poNo) {
        File poFile = new File(PO_FILENAME);
        boolean entryFound = false;
        StringBuilder updatedContent = new StringBuilder();

        // First, read the file and filter the content
        try (BufferedReader br = new BufferedReader(new FileReader(poFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 13 && parts[0].trim().equals(poNo)) {
                    // If the poNo matches, mark it as found and log the deletion
                    entryFound = true;

                    try (BufferedWriter logBw = new BufferedWriter(new FileWriter(PO_LOG_FILENAME, true))) {
                        logBw.write("Deleted Entry: " + line + " | Timestamp: " + new java.util.Date());
                        logBw.newLine();

                        logBw.close();
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(null, "Error logging deleted purchase order.");
                    }
                } else {
                    // Append non-matching lines to the updated content
                    updatedContent.append(line).append(System.lineSeparator());
                }
            }

            br.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading purchase_order.txt.");
            return;
        }

        // Next, write the updated content back to the original file
        if (entryFound) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(poFile))) {
                bw.write(updatedContent.toString());
                bw.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error writing to purchase_order.txt.");
                return;
            }
            JOptionPane.showMessageDialog(null, "Purchase order deleted successfully.");
        } else {
            JOptionPane.showMessageDialog(null, "Purchase order not found.");
        }
    }
}
