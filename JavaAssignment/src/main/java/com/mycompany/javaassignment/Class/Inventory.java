package com.mycompany.javaassignment.Class;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Inventory extends Item { //Stores Item Stock Details

    private int totalQty;
    private int qtyInProcess;
    private int qtyAvailable;

    private static final String INVENTORY_FILENAME = System.getProperty("user.dir") + "/src/assignment/Database/inventory.txt";
    private static final String STOCK_MOVEMENT_LOG_FILENAME = System.getProperty("user.dir") + "/src/assignment/Database/stockMovementLog.txt";

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
            stockMovementLog(itemNo, itemName, "Added Stock", "system");

        } catch (IOException e) {
            System.err.println("Error adding stock: " + e.getMessage());
        }
    }

    // Increase the quantity of an existing stock
    public void addStockQty(String itemNo, int qty) {
        try (BufferedReader br = new BufferedReader(new FileReader(INVENTORY_FILENAME))) {
            String line;
            List<String> eachLine = new ArrayList<>();
            boolean updated = false;

            // Read existing stock data and update quantity
            while ((line = br.readLine()) != null) {
                String[] data = line.split(", ");
                if (data[0].equals(itemNo)) {
                    int newQty = Integer.parseInt(data[2]) + qty;
                    String updatedLine = String.join(", ", itemNo, data[1], String.valueOf(newQty), data[3], data[4]);
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
                }

                // Log the stock increase
                stockMovementLog(itemNo, "Unknown", "Added " + qty + " items", "system");
            } else {
                System.err.println("Item not found in stock: " + itemNo);
            }

        } catch (IOException e) {
            System.err.println("Error adding stock quantity: " + e.getMessage());
        }
    }

    // Reduce the stock quantity
    public void reduceStock(String itemNo, int qty) {
        try (BufferedReader br = new BufferedReader(new FileReader(INVENTORY_FILENAME))) {
            String line;
            List<String> eachLine = new ArrayList<>();
            boolean updated = false;

            // Read existing stock data and reduce quantity
            while ((line = br.readLine()) != null) {
                String[] data = line.split(", ");
                if (data[0].equals(itemNo)) {
                    int newQty = Integer.parseInt(data[2]) - qty;
                    if (newQty < 0) {
                        System.err.println("Error: Insufficient stock to reduce.");
                        return;
                    }
                    String updatedLine = String.join(", ", itemNo, data[1], String.valueOf(newQty), data[3], data[4]);
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
                }

                // Log the stock reduction
                stockMovementLog(itemNo, "Unknown", "Reduced " + qty + " items", "system");
            } else {
                System.err.println("Item not found in stock: " + itemNo);
            }

        } catch (IOException e) {
            System.err.println("Error reducing stock: " + e.getMessage());
        }
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

    // Update stock
    public void editStock(String targetItemNo, String newItemName, int newTotalQty, int newQtyInProcess, int newQtyAvailable, String userID) {
        List<String> stockData = new ArrayList<>();
        boolean updated = false;
        String logEntry = "";

        try (BufferedReader br = new BufferedReader(new FileReader("inventory.txt"))) {
            // Read the stock count (if exists) and preserve it
            String line = br.readLine(); // Skip the first line (it contains the count, if any)
            stockData.add(line); // Add back the count as the first line

            while ((line = br.readLine()) != null) {
                String[] data = line.split(", ");
                if (data[0].equals(targetItemNo)) {
                    // Log the edit action with item name and stock details
                    CurrentTime currentTime = new CurrentTime();
                    logEntry = String.format("Edit Stock: %s, %s>%s, %d>%d, %d>%d, %d>%d, %s, %s",
                            data[0], 
                            data[1], newItemName,
                            Integer.parseInt(data[2]), newTotalQty,
                            Integer.parseInt(data[3]), newQtyInProcess,
                            Integer.parseInt(data[4]), newQtyAvailable,
                            userID, currentTime.toDateTimeFormat());

                    // Update the stock data (itemName, totalQty, qtyInProcess, qtyAvailable)
                    line = String.join(", ", targetItemNo, newItemName, String.valueOf(newTotalQty), String.valueOf(newQtyInProcess), String.valueOf(newQtyAvailable));
                    updated = true;
                }
                stockData.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading stock data for edit: " + e.getMessage());
        }

        if (updated) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("inventory.txt"))) {
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
    private void deleteStock(String itemNo) {
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
