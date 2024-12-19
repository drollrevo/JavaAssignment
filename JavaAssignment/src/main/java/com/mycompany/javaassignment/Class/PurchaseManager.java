package com.mycompany.javaassignment.Class;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class PurchaseManager extends User {

    private List<PurchaseOrder> purchaseOrders = null;
    private final String PO_FILENAME = System.getProperty("user.dir") + "\\src\\main\\java\\com\\mycompany\\javaassignment\\Database\\purchase_orders.txt";
    private final String PO_LOG_FILENAME = System.getProperty("user.dir") + "\\src\\main\\java\\com\\mycompany\\javaassignment\\Database\\purchase_orders_log.txt";

    CurrentTime time = new CurrentTime();

    public PurchaseManager() {
    }

    public PurchaseManager(int userId, String username, String password, Role role) {
        super(userId, username, password, role);
        this.purchaseOrders = new ArrayList<>();
    }

//    public void savePurchaseOrders() {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PO_FILENAME))) {
//            if (purchaseOrders.isEmpty()) {
//                System.out.println("No Purchase Orders to save.");
//                return;
//            }
//            for (PurchaseOrder po : purchaseOrders) {
//                writer.write(po.toString());
//                writer.newLine();
//            }
//            System.out.println("Purchase Orders saved to file.");
//        } catch (IOException e) {
//            System.out.println("Error saving Purchase Orders: " + e.getMessage());
//        }
//    }
    
    //Method to view all suppliers -call the statics method from the existing supplier class 
    public void viewSuppliers(List<Supplier> supplierList) {
        System.out.println("List of Suppliers:");
        if (supplierList == null || supplierList.isEmpty()) {
            System.out.println("No suppliers available.");
        } else {
            for (Supplier supplier : supplierList) {
                System.out.println(supplier);
            }
        }
    }

    //Method to view all items 
    public void viewItems(List<Item> itemList) {
        System.out.println("List of Items:");
        if (itemList == null || itemList.isEmpty()) {
            System.out.println("No items available.");
        } else {
            for (Item item : itemList) {
                System.out.println(item);
            }
        }
    }

}
