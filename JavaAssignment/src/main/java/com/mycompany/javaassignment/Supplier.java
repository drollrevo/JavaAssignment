package com.mycompany.javaassignment;

import java.util.ArrayList;
import java.util.List;

public class Supplier {
    private String supplierID;
    private String supplierName;
    private String address;
    private String supplierStatus;
    private String itemNo;
    private String itemName;
    private double price;

    // Static list to manage suppliers and logs
    private static List<Supplier> supplierList = new ArrayList<>();
    private static List<SupplierEntry> supplierLogs = new ArrayList<>();

    // Constructor
    public Supplier(String supplierID, String supplierName, String address, String supplierStatus, String itemNo, String itemName, double price) {
        this.supplierID = supplierID;
        this.supplierName = supplierName;
        this.address = address;
        this.supplierStatus = supplierStatus;
        this.itemNo = itemNo;
        this.itemName = itemName;
        this.price = price;

        // Automatically add to the supplier list
        supplierList.add(this);

        // Log the addition
        supplierLogs.add(new SupplierEntry("Added", this, "System"));
    }

    // Getters
    public String getSupplierID() {
        return supplierID;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getAddress() {
        return address;
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

    // Edit supplier
    public static void editSupplier(String supplierID, String newName, String newAddress, String newStatus, String newItemNo, String newItemName, double newPrice) {
        for (Supplier supplier : supplierList) {
            if (supplier.getSupplierID().equals(supplierID)) {
                supplier.setSupplierName(newName);
                supplier.setAddress(newAddress);
                supplier.setSupplierStatus(newStatus);
                supplier.setItemNo(newItemNo);
                supplier.setItemName(newItemName);
                supplier.setPrice(newPrice);

                // Log the edit
                supplierLogs.add(new SupplierEntry("Edited", supplier, "User123"));
                System.out.println("Supplier updated: " + supplierID);
                return;
            }
        }
        System.out.println("Supplier not found: " + supplierID);
    }

    // Delete supplier
    public static void deleteSupplier(String supplierID) {
        for (Supplier supplier : supplierList) {
            if (supplier.getSupplierID().equals(supplierID)) {
                supplierLogs.add(new SupplierEntry("Deleted", supplier, "User123"));
                supplierList.remove(supplier);
                System.out.println("Supplier deleted: " + supplierID);
                return;
            }
        }
        System.out.println("Supplier not found: " + supplierID);
    }

    // Display supplier list
    public static void supplierList() {
        System.out.println("Supplier List:");
        for (Supplier supplier : supplierList) {
            System.out.println("ID: " + supplier.getSupplierID() + ", Name: " + supplier.getSupplierName());
        }
    }
    //vIEW SUPPLIERS
    public static void viewSuppliers(){
        System.out.println("List of Suppliers");
        if(supplierList.isEmpty()){
            System.out.println("No suppliers avilable");
            
        }else{
            for(Supplier supplier:supplierList){
                System.out.println(supplier);
            }
        }
        
    }
    

    // Display Supplier logs
    public static void displayLogs() {
        System.out.println("Supplier Logs:");
        for (SupplierEntry log : supplierLogs) {
            log.displayLog();
            System.out.println("-------------------");
        }
    }
    //override tostring to display supplier details
    @Override
    public String toString(){
        return "Supplier ID: "+supplierID + ",Name: " +supplierName + ",Address: "+ address + ",Status: "+ supplierStatus + ",Item No: " + itemNo + ",Item Name: " + itemName + ",Price: " + price;
        
    }
}