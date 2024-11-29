package com.mycompany.javaassignment;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PurchaseManager extends User {

    private final List<PurchaseOrder> purchaseOrders;

    public PurchaseManager(int userId, String username, String password, Role role) {
        super(userId, username, password, role);
        this.purchaseOrders = new ArrayList<>();
    }

    // Method to add a new Purchase Order
    public void addPurchaseOrder(PurchaseOrder po) {
        purchaseOrders.add(po);
        System.out.println("Purchase Order added: " + po);
    }

    public void savePurchaseOrders() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Hannah\\OneDrive\\Desktop\\hannah - java assignment\\JavaAssignment\\JavaAssignment\\src\\main\\java\\com\\mycompany\\javaassignment\\purchase_orders.txt", true))) {
        if (purchaseOrders.isEmpty()){
            System.out.println("No Purchase Orders to save.");
            return;
        }       
        for (PurchaseOrder po : purchaseOrders) {
            writer.write(po.toString());  
            writer.newLine();
        }
        System.out.println("Purchase Orders saved to file.");
    } catch (IOException e) {
        System.out.println("Error saving Purchase Orders: " + e.getMessage());
    }
    }

    // Method to delete a Purchase Order by poNo
    public void deletePurchaseOrder(String poNo) {
    boolean isRemoved = purchaseOrders.removeIf(po -> po.getPoNo().equals(poNo)); 
    if (isRemoved) {
        System.out.println("Purchase Order with PO No " + poNo + " has been deleted.");
    } else {
        System.out.println("Purchase Order with PO No " + poNo + " not found.");
    }
}


    // Method to edit an existing Purchase Order's status and other fields
    public void editPurchaseOrder(String poNo, String newStatus, int newQty, String newRemarks) {
        for (PurchaseOrder po : purchaseOrders) {
            if (po.getPoNo().equals(poNo)) {
                po.setPoStatus(newStatus);
                po.setPurchaseQty(newQty);
                po.setOrderRemarks(newRemarks);
                System.out.println("Purchase Order " + poNo + " updated: " + po);
                return;
            }
        }
        System.out.println("Purchase Order with number " + poNo + " not found.");
    }

    // Method to view all Purchase Orders
    public void viewPurchaseOrders() {
        System.out.println("List of Purchase Orders:");
        for (PurchaseOrder po : purchaseOrders) {
            System.out.println(po);
        }
    }
}
