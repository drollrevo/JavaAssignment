package com.mycompany.javaassignment.Class;

import com.mycompany.javaassignment.Class.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class Inventory extends Item { //Stores Item Stock Details

    private int totalQty;
    private int qtyInProcess;
    private int qtyAvailable;

    private static final String INVENTORY_FILENAME = System.getProperty("user.dir") + "/src/main/java/com/mycompany/javaassignment/Database/inventory.txt";
    private static final String STOCK_MOVEMENT_LOG_FILENAME = System.getProperty("user.dir") + "/src/main/java/com/mycompany/javaassignment/Database/stockMovementLog.txt";
    private static final String DELIVERY_FILENAME = System.getProperty("user.dir") + "/src/main/java/com/mycompany/javaassignment/Database/delivery.txt";

    CurrentTime time = new CurrentTime();
    User user = new User() {
    };

    // Constructors
    public Inventory() {

    }

    // Getters and Setters
    public int getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(int totalQty) {
        this.totalQty = totalQty;
    }

    public int getQtyInProcess() {
        return qtyInProcess;
    }

    public void setQtyInProcess(int qtyInProcess) {
        this.qtyInProcess = qtyInProcess;
    }

    public int getQtyAvailable() {
        return qtyAvailable;
    }

    public int getQtyAvailable(String itemNo) {
        this.qtyAvailable = 0;

        try {
            FileReader fr = new FileReader(INVENTORY_FILENAME);
            BufferedReader br = new BufferedReader(fr);

            String line;

            br.readLine();
            // Read each line from the file            
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(","); // Assuming CSV format: itemNo,itemName,...

                String currentItemNo = parts[0].trim();
                int currentQtyAvailable = Integer.parseInt(parts[2].trim());

                // Check if the itemNo matches the current line's itemNo and itemStatus not inactive
                if (currentItemNo.equals(itemNo)) {
                    qtyAvailable = currentQtyAvailable; // Set the itemName if itemNo matches
                    break; // No need to continue searching after finding the item
                }
            }

            fr.close();
            br.close();

        } catch (IOException e) {
            System.out.println("item.txt not found.");
        }

        return qtyAvailable;
    }

    public void setQtyAvailable(int qtyAvailable) {
        this.qtyAvailable = qtyAvailable;
    }

    /*Methods*/
    // Add Stock when an item is added from the Item class or its status changes from inactive to active
    public void addStock(String itemNo, String itemName) {
        try (BufferedReader br = new BufferedReader(new FileReader(INVENTORY_FILENAME))) {
            String line;
            List<String> eachLine = new ArrayList<>();
            boolean itemExists = false;

            // Read existing stock data
            while ((line = br.readLine()) != null) {
                eachLine.add(line);
                String[] data = line.split(", ");
                if (data[0].equals(itemNo)) {
                    itemExists = true;
                    break;
                }
            }

            if (!itemExists) {
                // If the item doesn't exist, add it with default quantities
                String newItem = String.join(", ", itemNo, itemName, "0", "0", "0");
                eachLine.add(newItem);
            }

            // Write updated stock data back to the file
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(INVENTORY_FILENAME))) {
                for (String itemLine : eachLine) {
                    bw.write(itemLine);
                    bw.newLine();
                }
            }

            // Log the stock addition
            stockMovementLog(itemNo, itemName, "Added Stock", user.getCurrentUserID());

        } catch (IOException e) {
            System.err.println("Error adding stock: " + e.getMessage());
        }
    }

    // Increase the quantity of an existing stock
    public void addStockQty(String itemNo, String itemName, int qty) {
        try (BufferedReader br = new BufferedReader(new FileReader(INVENTORY_FILENAME))) {
            String line;
            List<String> eachLine = new ArrayList<>();
            boolean updated = false;

            // Read existing stock data and update quantity
            while ((line = br.readLine()) != null) {
                String[] data = line.split(", ");
                if (data[0].equals(itemNo)) {
                    String updatedLine = String.join(", ", itemNo, data[1], String.valueOf(Integer.parseInt(data[2]) + qty), String.valueOf(Integer.parseInt(data[3]) - qty), data[4]);
                    eachLine.add(updatedLine);
                    updated = true;
                } else {
                    eachLine.add(line);
                }
            }

            if (updated) {
                // Write updated stock data back to the file
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(INVENTORY_FILENAME))) {
                    for (String itemLine : eachLine) {
                        bw.write(itemLine);
                        bw.newLine();
                    }
                    JOptionPane.showMessageDialog(null, "Stock Updated");
                    stockMovementLog(itemNo, itemName, "Added " + qty + " AvailableStock", user.getCurrentUserID());
                    bw.close();
                }
            } else {
                System.err.println("Item not found in stock: " + itemNo);
            }

        } catch (IOException e) {
            System.err.println("Error adding stock quantity: " + e.getMessage());
        }
    }

    public void addStockQty(String poNo, String itemNo, String itemName, int qty, String type) {
        try (BufferedReader br = new BufferedReader(new FileReader(INVENTORY_FILENAME))) {
            String line;
            List<String> eachLine = new ArrayList<>();
            boolean updated = false;

            // Read existing stock data and update quantity
            while ((line = br.readLine()) != null) {
                String[] data = line.split(", ");
                if (data[0].equals(itemNo)) {
                    String updatedLine = "";
                    switch (type) {
                        case "SIP":
                            updatedLine = String.join(", ", itemNo, data[1], data[2], String.valueOf(Integer.parseInt(data[3]) + qty), String.valueOf(Integer.parseInt(data[4]) + qty));
                            break;
                        case "SA":
                            updatedLine = String.join(", ", itemNo, data[1], String.valueOf(Integer.parseInt(data[2]) + qty), String.valueOf(Integer.parseInt(data[3]) - qty), data[4]);
                            break;
                    }
                    eachLine.add(updatedLine);
                    updated = true;
                } else {
                    eachLine.add(line);
                }
            }

            if (updated) {
                // Write updated stock data back to the file
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(INVENTORY_FILENAME))) {
                    for (String itemLine : eachLine) {
                        bw.write(itemLine);
                        bw.newLine();
                    }
                    switch (type) {
                        case "SIP":
                            stockMovementLog(itemNo, itemName, "Added " + qty + " StockInProgress", user.getCurrentUserID());
                            break;
                        case "SA":
                            stockMovementLog(itemNo, itemName, "Added " + qty + " AvailableStock", user.getCurrentUserID());
                            break;
                    }
                    JOptionPane.showMessageDialog(null, "Stock Updated");

                    bw.close();
                }

                if (type.equals("SA")) {
                    try (BufferedWriter bw = new BufferedWriter(new FileWriter(DELIVERY_FILENAME, true))) {
                        String itemLine = String.join(",", new String[]{
                            poNo, itemNo, itemName, String.valueOf(qty), time.toDateFormat(), user.getCurrentUserID()
                        });

                        bw.write(itemLine);
                        bw.newLine();

                        bw.close();
                    }
                }
            } else {
                System.err.println("Item not found in stock: " + itemNo);
            }

        } catch (IOException e) {
            System.err.println("Error adding stock quantity: " + e.getMessage());
        }
    }

    // Reduce the stock quantity
    public boolean reduceStock(String itemNo, String itemName, int qty, String type) {
        try (BufferedReader br = new BufferedReader(new FileReader(INVENTORY_FILENAME))) {
            String line;
            List<String> eachLine = new ArrayList<>();
            boolean updated = false;

            // Read existing stock data and reduce quantity
            while ((line = br.readLine()) != null) {
                String[] data = line.split(", ");
                if (data[0].equals(itemNo)) {
                    int newQty = (Integer.parseInt(data[2]) - qty);
                    if (newQty < 0) {
                        JOptionPane.showMessageDialog(null, "Error: Insufficient stock to reduce.");
                        return false;
                    } else {

                        String updatedLine = "";
                        switch (type) {
                            case "SIP":
                                updatedLine = String.join(", ", itemNo, data[1], data[2], String.valueOf(Integer.parseInt(data[3]) - qty), String.valueOf(Integer.parseInt(data[4]) - qty));
                                break;
                            case "SA":
                                updatedLine = String.join(", ", itemNo, data[1], String.valueOf(Integer.parseInt(data[2]) - qty), data[3], String.valueOf(Integer.parseInt(data[4]) - qty));
                                break;
                        }
                        eachLine.add(updatedLine);
                        updated = true;
                    }
                } else {
                    eachLine.add(line);
                }
            }

            if (updated) {
                // Write updated stock data back to the file
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(INVENTORY_FILENAME))) {
                    for (String itemLine : eachLine) {
                        bw.write(itemLine);
                        bw.newLine();
                    }
                    JOptionPane.showMessageDialog(null, "Stock Updated");
                }

                // Log the stock reduction
                switch (type) {
                    case "SIP":
                        stockMovementLog(itemNo, itemName, "Reduced " + qty + " StockInProgress", user.getCurrentUserID());
                        break;
                    case "SA":
                        stockMovementLog(itemNo, itemName, "Reduced " + qty + " AvailableStock", user.getCurrentUserID());
                        break;
                }

                return true;
            } else {
                System.err.println("Item not found in stock: " + itemNo);
            }

        } catch (IOException e) {
            System.err.println("Error reducing stock: " + e.getMessage());
        }

        return false;
    }

    // Display all inventory stock
    public void inventoryList() {
        try (BufferedReader br = new BufferedReader(new FileReader(INVENTORY_FILENAME))) {
            String line;
            System.out.println("|ItemNo.|Item Name|TotalQty|QtyInProcess|QtyAvailable|");

            while ((line = br.readLine()) != null) {
                String[] data = line.split(", ");
                // Check if the item exists and is active in item.txt
                if (isItemActive(data[0])) {
                    System.out.println(line);
                } else {
                    // If not active, delete the stock entry
                    deleteStock(data[0]);
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading inventory list: " + e.getMessage());
        }
    }

    // Check if an item is active
    private boolean isItemActive(String itemNo) {
        try (BufferedReader br = new BufferedReader(new FileReader("item.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(", ");
                if (data[0].equals(itemNo) && data[3].equals("active")) {  // Assuming column 3 stores the status (active/inactive)
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading item file: " + e.getMessage());
        }
        return false;
    }

    public void editStock(String targetItemNo, String newItemName, String reason, String description, int newQtyAvailable, int qtyInProcess, int newTotalQty, String userID) {
        List<String> stockData = new ArrayList<>();
        boolean updated = false;
        String logEntry = "";
        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader(INVENTORY_FILENAME))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(", ");
                if (data[0].equals(targetItemNo)) {
                    // Log entry format
                    CurrentTime currentTime = new CurrentTime();
                    logEntry = String.format(
                            "Edit Stock: ItemNo=%s, OldName=%s, NewName=%s, Reason=%s, Description=%s, Available=%s->%d, InProgress=%d, TotalQty=%s->%d, Edited By: %s, Time=%s",
                            targetItemNo,
                            data[1], newItemName,
                            reason, description,
                            data[2], newQtyAvailable,
                            qtyInProcess,
                            data[4], newTotalQty,
                            userID,
                            currentTime.toDateTimeFormat()
                    );

                    line = String.join(", ", targetItemNo, newItemName, String.valueOf(newQtyAvailable), String.valueOf(qtyInProcess), String.valueOf(newTotalQty));
                    updated = true;
                }
                stockData.add(line);

            }
        } catch (IOException e) {
            System.err.println("Error reading stock data: " + e.getMessage());
        }

        // Write updated data back to the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(INVENTORY_FILENAME))) {
            for (String stock : stockData) {
                bw.write(stock);
                bw.newLine();
            }
            JOptionPane.showMessageDialog(null, "Stock Updated Successfully.");
        } catch (IOException e) {
            System.err.println("Error opening inventory.txt " + e.getMessage());
        }

        // Append log entry to the movement log
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(STOCK_MOVEMENT_LOG_FILENAME, true))) {
            bw.write(logEntry);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error writing stock movement log data: " + e.getMessage());
        }
    }

    // Update stock
    public void editStock(String targetItemNo, String newItemName, String userID) {
        List<String> stockData = new ArrayList<>();
        boolean updated = false;
        String logEntry = "";
        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader(INVENTORY_FILENAME))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(", ");
                if (data[0].equals(targetItemNo)) {
                    // Log the edit action with item name and stock details
                    CurrentTime currentTime = new CurrentTime();
                    logEntry = String.format("Edit Stock: %s, %s>%s， %s, %s",
                            data[0],
                            data[1], newItemName,
                            userID, currentTime.toDateTimeFormat());

                    // Update the stock data (itemName, totalQty, qtyInProcess, qtyAvailable)
                    line = String.join(", ", targetItemNo, newItemName, data[2], data[3], data[4]);
                    updated = true;
                }
                stockData.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading stock data for edit: " + e.getMessage());
        }

        if (updated) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(INVENTORY_FILENAME))) {
                for (String stock : stockData) {
                    bw.write(stock);
                    bw.newLine();
                }
            } catch (IOException e) {
                System.err.println("Error writing updated stock data: " + e.getMessage());
            }

            // Log the changes
            stockMovementLog(logEntry);
        } else {
            System.out.println("Stock not found for item number: " + targetItemNo);
        }
    }

    // Delete stock entry from inventory.txt
    public void deleteStock(String itemNo) {
        try (BufferedReader br = new BufferedReader(new FileReader(INVENTORY_FILENAME))) {
            String line;
            List<String> eachLine = new ArrayList<>();

            // Read existing inventory and exclude the inactive item
            while ((line = br.readLine()) != null) {
                if (!line.startsWith(itemNo)) {
                    eachLine.add(line);
                }
            }

            // Write updated inventory data back to the file
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(INVENTORY_FILENAME))) {
                for (String itemLine : eachLine) {
                    bw.write(itemLine);
                    bw.newLine();
                }
            }

        } catch (IOException e) {
            System.err.println("Error deleting stock: " + e.getMessage());
        }
    }

    // Log stock movements
    public void stockMovementLog(String itemNo, String itemName, String action, String userID) {
        try (BufferedWriter logWriter = new BufferedWriter(new FileWriter(STOCK_MOVEMENT_LOG_FILENAME, true))) {
            String logEntry = String.format("ItemNo: %s, ItemName: %s, Action: %s, User: %s, DateTime: %s",
                    itemNo, itemName, action, userID, new CurrentTime().toDateTimeFormat());
            logWriter.write(logEntry);
            logWriter.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to stock movement log: " + e.getMessage());
        }
    }

    public void stockMovementLog(String logEntry) {
        try (BufferedWriter logWriter = new BufferedWriter(new FileWriter(STOCK_MOVEMENT_LOG_FILENAME, true))) {
            logWriter.write(logEntry);
            logWriter.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to stock movement log: " + e.getMessage());
        }
    } // Overloading
}
