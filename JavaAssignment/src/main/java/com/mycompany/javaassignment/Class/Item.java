package com.mycompany.javaassignment.Class;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class Item {

    private String itemNo;
    private String itemName;
    private String itemStatus;
    private double itemPrice;

    //private static final String ITEM_FILENAME = System.getProperty("user.dir") + "/src/Database/item.txt";
    //private static final String LOG_FILENAME = System.getProperty("user.dir") + "/src/Database/itemLog.txt";
    private static final String ITEM_FILENAME = System.getProperty("user.dir") + "/src/main/java/com/mycompany/javaassignment/Database/item.txt";
    private static final String LOG_FILENAME = System.getProperty("user.dir") + "/src/main/java/com/mycompany/javaassignment/Database/itemLog.txt";

    // Constructor
    public Item() {
    }

    public Item(String itemNo, String itemName, double itemPrice, String itemStatus) {
        this.itemNo = itemNo;
        this.itemName = itemName;
        this.itemStatus = itemStatus;
        this.itemPrice = itemPrice;
    }

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
                String currentItemStatus = parts[3].trim();

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

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public double getItemPrice(String itemNo) {
        this.itemPrice = 0; // Initialize itemName as null

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
                String currentItemStatus = parts[3].trim();

                // Check if the itemNo matches the current line's itemNo and itemStatus not inactive
                if (currentItemNo.equals(itemNo) && currentItemStatus.equals("ACTIVE")) {
                    this.itemPrice = currentPrice; // Set the itemName if itemNo matches
                    break; // No need to continue searching after finding the item
                }
            }

            fr.close();
            br.close();

        } catch (IOException e) {
            System.out.println("item.txt not found.");
        }

        return itemPrice; // Will return null if itemNo not found
    }

    /* Methods */
//    public void itemList() {
//        try (BufferedReader br = new BufferedReader(new FileReader(ITEM_FILENAME))) {
//            String line;
//
//            // Skip the first line (item counter)
//            br.readLine();
//
//            System.out.println("|ItemNo.|Item Name|SupplierID|Item Status|");
//            while ((line = br.readLine()) != null) {
//                System.out.println(line);
//            }
//        } catch (IOException e) {
//            System.err.println("Error reading item list: " + e.getMessage());
//        }
//    }
    public List<Item> itemList() {
        List<Item> items = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(ITEM_FILENAME))) {
            String line;

            br.readLine();
            while ((line = br.readLine()) != null) {
                // Split the line into variables
                String[] parts = line.split(",");
                if (parts.length == 4) { // Ensure correct number of fields
                    String itemNo = parts[0].trim();
                    String itemName = parts[1].trim();
                    double itemPrice = Double.parseDouble(parts[2].trim());
                    String status = parts[3].trim();
                    // Create a Item object and add it to the list
                    items.add(new Item(itemNo, itemName, itemPrice, status));
                }
            }

            br.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to Open Item.txt.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid number format in Item.txt.");
        }

        return items;
    }

    public void addItem(String itemNo, String itemName, String price) {
        String itemStatus = "ACTIVE";

        try {
            // Step 1: Read the entire file into memory
            List<String> fileContents = new ArrayList<>();
            int itemCount = 0;

            try (BufferedReader br = new BufferedReader(new FileReader(ITEM_FILENAME))) {
                String line = br.readLine(); // Read the first line (item count)
                if (line != null && !line.isEmpty()) {
                    itemCount = Integer.parseInt(line.trim());
                }

                String dataLine;
                while ((dataLine = br.readLine()) != null) {
                    fileContents.add(dataLine);
                    String[] data = dataLine.split(", ");
                    // Check for duplicate itemNo or itemName
                    if (data[0].equalsIgnoreCase(itemNo)) {
                        System.err.println("Error: Item No already exists.");
                        return;
                    } else if (data[1].equalsIgnoreCase(itemName)) {
                        System.err.println("Error: Item name already exists.");
                        return;
                    }
                }
            }

            // Step 2: Update item count and prepare the new item
            itemCount++;
            String newItem = String.join(", ", itemNo, itemName, price, itemStatus);
            fileContents.add(newItem);

            // Step 3: Write the updated content back to the file
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(ITEM_FILENAME))) {
                bw.write(String.valueOf(itemCount)); // Write the updated item count
                bw.newLine();
                for (String line : fileContents) {
                    bw.write(line);
                    bw.newLine();
                }
            }

            // Log the addition
            CurrentTime currentTime = new CurrentTime();
            String logEntry = String.format("New item added: %s, %s", newItem, currentTime.toDateTimeFormat());
            itemLog(logEntry);

            // Add the new item to the inventory
            Inventory inventory = new Inventory();
            inventory.addStock(itemNo, itemName);

        } catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error adding new item: " + e.getMessage());
        }
    }

    public void editItem(String targetItemNo, String newItemName, String price, String newItemStatus, String userID) {
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
                            data[2], price,
                            data[3], newItemStatus,
                            userID, currentTime.toDateTimeFormat());

                    line = String.join(", ", targetItemNo, newItemName, price, newItemStatus);
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
                Inventory inventory = new Inventory();
                inventory.editStock(targetItemNo, newItemName, userID);
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
                    Inventory inventory = new Inventory();
                    inventory.deleteItem(itemNoToDelete);

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
