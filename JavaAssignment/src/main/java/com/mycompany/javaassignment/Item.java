package com.mycompany.javaassignment;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Item {

    private String itemNo;
    private String itemName;
    private String supplierID;
    private String itemStatus;
    private double itemPrice;

    private static final String ITEM_FILENAME = System.getProperty("user.dir") + "/src/Database/item.txt";
    private static final String LOG_FILENAME = System.getProperty("user.dir") + "/src/Database/itemLog.txt";

    // Constructor
    public Item() {
    }

    public Item(String itemNo, String itemName, String supplierID, String itemStatus,double itemprice) {
        this.itemNo = itemNo;
        this.itemName = itemName;
        this.supplierID = supplierID;
        this.itemStatus = itemStatus;
        this.itemPrice=itemPrice;
    }
    //Item item = new Item("i001", "Chocolate Bar", "suP001", "Available", "");
    //Item(String i001, String chocolate_Bar, String suP001, String available, double d) {
    //   throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    //}

    /*Item(String I001, String chocolate_Bar, String suP001, String available, double d) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }*/

    // Getters and Setters
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
    
    public double getItemPrice() {
        return itemPrice;
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

    @Override
    public String toString() {
        return "Item{" + "itemNo='" + itemNo + '\'' + ", itemName='" + itemName + '\'' + ", supplierID='" + supplierID + '\'' + ", itemStatus='" + itemStatus + '\'' + '}';
    }

    // Testing
    public static void main(String[] args) {
        Item item = new Item();

        // Add items for testing
//        item.addItem("Television", "SUP-2024-KL-001", "ACTIVE");
//        item.addItem("Vacuum", "SUP-2024-KL-002", "INACTIVE");
        // Display the initial list
//        System.out.println("Initial Item List:");
//        item.itemList();

        // Edit an item
//        item.deleteItem("PROD-10002");
        // Display the updated list
//        System.out.println("Deleted Item List:");
//        item.itemList();

        item.addItem("Panasonic Fridge", "SUP-2024-KL-001", "ACTIVE");
        System.out.println("Latest Item List:");
        item.itemList();
    }
}







/*public class Item {
    private String itemNo;
    private String itemName;
    private String supplierID;
    private String itemStatus;
    private double itemPrice;

    public Item(String itemNo, String itemName, String supplierID, String itemStatus, double itemPrice) {
        this.itemNo = itemNo;
        this.itemName = itemName;
        this.supplierID = supplierID;
        this.itemStatus = itemStatus;
        this.itemPrice = itemPrice;
    }

    // Getter methods
    public String getItemNo() {
        return itemNo;
    }

    public String getItemName() {
        return itemName;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    @Override
    public String toString() {
        return "Item No: " + itemNo + ", Name: " + itemName + ", Supplier ID: " + supplierID + ", Status: " + itemStatus + 
               ", Price: " + itemPrice;
    }
}*/
