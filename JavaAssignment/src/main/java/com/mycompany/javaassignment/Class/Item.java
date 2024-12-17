package com.mycompany.javaassignment.Class;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Item {

    private String itemNo;
    private String itemName;
    private double pricePerUnit;
    private String supplierID;
    private String itemStatus;

    private static final String ITEM_FILENAME = System.getProperty("user.dir") + "\\src\\main\\java\\com\\mycompany\\javaassignment\\Database\\item.txt";
    private static final String LOG_FILENAME = System.getProperty("user.dir") + "\\src\\main\\java\\com\\mycompany\\javaassignment\\Database\\itemLog.txt";

    // Constructor
    public Item() {
    }

    public Item(String itemNo, String itemName, String supplierID, String itemStatus) {
        this.itemNo = itemNo;
        this.itemName = itemName;
        this.supplierID = supplierID;
        this.itemStatus = itemStatus;
    }

    // Getters and Setters
    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getItemName(String itemNo) {
        this.itemName = null; // Initialize itemName as null

        try {
            FileReader fr = new FileReader(ITEM_FILENAME);
            BufferedReader br = new BufferedReader(fr);

            String line;

            br.readLine();
            // Read each line from the file            
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(","); // Assuming CSV format: itemNo,itemName,...

                String currentItemNo = parts[0].trim();
                String currentItemName = parts[1].trim();
                String currentItemStatus = parts[4].trim();

                // Check if the itemNo matches the current line's itemNo and itemStatus not inactive
                if (currentItemNo.equals(itemNo) && currentItemStatus.equals("ACTIVE")) {
                    itemName = currentItemName; // Set the itemName if itemNo matches
                    break; // No need to continue searching after finding the item
                }
            }

            fr.close();
            br.close();

        } catch (IOException e) {
            System.out.println("item.txt not found.");
        }

        return itemName; // Will return null if itemNo not found
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPricePerUnit(String itemNo) {
        this.pricePerUnit = 0; // Initialize itemName as null

        try {
            FileReader fr = new FileReader(ITEM_FILENAME);
            BufferedReader br = new BufferedReader(fr);

            String line;

            br.readLine();
            // Read each line from the file            
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(","); // Assuming CSV format: itemNo,itemName,...

                String currentItemNo = parts[0].trim();
                double currentPrice = Double.parseDouble(parts[2].trim());
                String currentItemStatus = parts[4].trim();

                // Check if the itemNo matches the current line's itemNo and itemStatus not inactive
                if (currentItemNo.equals(itemNo) && currentItemStatus.equals("ACTIVE")) {
                    this.pricePerUnit = currentPrice; // Set the itemName if itemNo matches
                    break; // No need to continue searching after finding the item
                }
            }

            fr.close();
            br.close();

        } catch (IOException e) {
            System.out.println("item.txt not found.");
        }

        return pricePerUnit; // Will return null if itemNo not found
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    /* Methods */
    public void itemList() {
        try (BufferedReader br = new BufferedReader(new FileReader(ITEM_FILENAME))) {
            String line;

            // Skip the first line (item counter)
            br.readLine();

            System.out.println("|ItemNo.|Item Name|SupplierID|Item Status|");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading item list: " + e.getMessage());
        }
    }

    public void addItem(String itemName, String supplierID, String itemStatus) {
        try (BufferedReader br = new BufferedReader(new FileReader(ITEM_FILENAME))) {
            // Read the item count from the first line
            List<String> eachLine = new ArrayList<>();
            String line = br.readLine();
            int itemCount = 0;

            // If the first line is not empty, parse the item count
            if (line != null && !line.isEmpty()) {
                itemCount = Integer.parseInt(line.trim());
            }

            // Check for duplicate item name
            String dataLine;
            while ((dataLine = br.readLine()) != null) {
                eachLine.add(dataLine);
                String[] data = dataLine.split(", ");
                if (data[1].equalsIgnoreCase(itemName)) {
                    System.err.println("Error: Item name already exists.");
                    return;
                }
            }

            // Generates new itemNo
            itemCount++;
            String itemNo = String.format("PROD-%05d", itemCount + 10000);

            // Create the new item string
            String newItem = String.join(", ", itemNo, itemName, supplierID, itemStatus);

//            try (BufferedWriter bw = new BufferedWriter(new FileWriter(ITEM_FILENAME))) {
//                bw.write(String.valueOf(itemCount));  // Write the updated item count
//                bw.newLine();
//                bw.close();
//            }
            // Append the new item at the end of the file
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(ITEM_FILENAME))) {
                bw.write(String.valueOf(itemCount));  // Write the updated item count
                bw.newLine();
                for (String line1 : eachLine) {
                    bw.write(line1);
                    bw.newLine();
                }
                bw.write(newItem);
                bw.newLine();
            }

            // Log the addition
            CurrentTime currentTime = new CurrentTime();
            String logEntry = String.format("New item added: %s, %s", newItem, currentTime.toDateTimeFormat());
            itemLog(logEntry);

            Inventory inventory = new Inventory();
            inventory.addStock(itemNo, itemName);

        } catch (IOException | NumberFormatException e) {
            System.err.println("Error adding new item: " + e.getMessage());
        }
    }

    public void editItem(String targetItemNo, String newItemName, String newSupplierID, String newItemStatus, String userID) {
        List<String> items = new ArrayList<>();
        boolean updated = false;
        String logEntry = "";

        try (BufferedReader br = new BufferedReader(new FileReader(ITEM_FILENAME))) {
            // Read the item count and preserve it
            int itemCount = Integer.parseInt(br.readLine());
            items.add(String.valueOf(itemCount)); // Add back the counter as the first line

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(", ");
                if (data[0].equals(targetItemNo)) {
                    CurrentTime currentTime = new CurrentTime();
                    logEntry = String.format("Edit Item: %s, %s>%s, %s>%s, %s>%s, %s, %s",
                            data[0],
                            data[1], newItemName,
                            data[2], newSupplierID,
                            data[3], newItemStatus,
                            userID, currentTime.toDateTimeFormat());

                    line = String.join(", ", targetItemNo, newItemName, newSupplierID, newItemStatus);
                    updated = true;
                }
                items.add(line);
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading items for edit: " + e.getMessage());
        }

        if (updated) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(ITEM_FILENAME))) {
                for (String item : items) {
                    bw.write(item);
                    bw.newLine();
                }
            } catch (IOException e) {
                System.err.println("Error writing updated items: " + e.getMessage());
            }

            // Log the changes
            itemLog(logEntry);
        } else {
            System.out.println("Item not found: " + targetItemNo);
        }
    }

    public void deleteItem(String itemNoToDelete) {
        List<String> items = new ArrayList<>();
        boolean deleted = false;
        String logEntry = "";

        try (BufferedReader br = new BufferedReader(new FileReader(ITEM_FILENAME))) {
            int itemCount = Integer.parseInt(br.readLine());
            items.add(String.valueOf(itemCount)); // Add back the counter as the first line

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(", ");
                if (data[0].equals(itemNoToDelete)) {
                    CurrentTime currentTime = new CurrentTime();
                    logEntry = String.format("Delete Item: %s, %s, %s", data[0], data[1], currentTime.toDateTimeFormat());
                    deleted = true;
                    continue; // Skip adding this item to the list
                }
                items.add(line);
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading items for delete: " + e.getMessage());
        }

        if (deleted) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(ITEM_FILENAME))) {
                for (String item : items) {
                    bw.write(item);
                    bw.newLine();
                }
            } catch (IOException e) {
                System.err.println("Error writing updated items after delete: " + e.getMessage());
            }

            // Log the deletion
            itemLog(logEntry);
        } else {
            System.out.println("Item not found: " + itemNoToDelete);
        }
    }

    public void itemLog(String logEntry) {
        try (BufferedWriter logWriter = new BufferedWriter(new FileWriter(LOG_FILENAME, true))) {
            logWriter.write(logEntry);
            logWriter.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

}
