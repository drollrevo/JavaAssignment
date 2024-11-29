package com.mycompany.javaassignment;

public class Item {
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
}
