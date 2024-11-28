package com.mycompany.javaassignment;
//Extend from User

// FinanceManager extends User class

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FM extends User {
    private final List<PurchaseOrder> purchaseOrders;
    private final List<Payment> payments;
    

    public FM(int userId, String username, String password, Role role) {
        super(userId, username, password, role);
        this.purchaseOrders = new ArrayList<>();
        this.payments = new ArrayList<>();
    }

    // Method to view all Purchase Orders
    public void viewPurchaseOrders() {
        System.out.println("List of Purchase Orders:");
        for (PurchaseOrder po : purchaseOrders) {
            System.out.println(po);
        }
    }

    // Method to verify and approve/reject a Purchase Order
    public void verifyPurchaseOrder(String poNo, String status) {
        for (PurchaseOrder po : purchaseOrders) {
            if (po.getPoNo().equals(poNo)) {
                po.setPoStatus(status);
                System.out.println("Purchase Order " + poNo + " has been " + status.toLowerCase() + ".");
                return;
            }
        }
        System.out.println("Purchase Order with number " + poNo + " not found.");
    }

    // Method to make a payment and update the Purchase Order status
    public void makePayment(Payment payment) {
        for (PurchaseOrder po : purchaseOrders) {
            if (po.getPoNo().equals(payment.getPoNo())) {
                po.setPoStatus("Paid");
                addPayment(payment);
                System.out.println("Payment made for Purchase Order " + payment.getPoNo());
                return;
            }
        }
        System.out.println("Purchase Order with number " + payment.getPoNo() + " not found.");
    }

    // Method to add a new payment
    public void addPayment(Payment payment) {
        payments.add(payment);
        System.out.println("Payment added: " + payment);
    }

    // Method to view payment history
    public void paymentHistory() {
        System.out.println("Payment History:");
        for (Payment payment : payments) {
            System.out.println(payment);
        }
    }

    // Method to edit an existing payment
    public void editPayment(String paymentNo, double newAmount, String newPaymentStatus) {
        for (Payment payment : payments) {
            if (payment.getPaymentNo().equals(paymentNo)) {
                payment.setAmount(newAmount);
                payment.setPaymentStatus(newPaymentStatus);
                System.out.println("Payment updated: " + payment);
                return;
            }
        }
        System.out.println("Payment with number " + paymentNo + " not found.");
    }

    // Method to delete a payment
    public void deletePayment(String paymentNo) {
        for (int i = 0; i < payments.size(); i++) {
            if (payments.get(i).getPaymentNo().equals(paymentNo)) {
                System.out.println("Payment deleted: " + payments.get(i));
                payments.remove(i);
                return;
            }
        }
        System.out.println("Payment with number " + paymentNo + " not found.");
    }

    // Method to view supplier payment status
    public void viewSupplierPaymentStatus(String supplierID) {
        System.out.println("Payment Status for Supplier ID: " + supplierID);
        for (Payment payment : payments) {
            if (payment.getSupplierID().equals(supplierID)) {
                System.out.println(payment);
            }
        }
    }

    // Method to add a PurchaseOrder
//    public void addPurchaseOrder(PurchaseOrder po) {
//        purchaseOrders.add(po);
//    }

    // Method to save the user (Saving functionality from the previous FM class)
    public void saveUser() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS, true))) {
            writer.write(getUserId() + "," + getUsername() + "," + getPassword() + "," + getRole() + "," + isActive() + "\n");
            System.out.println(getUsername() + " was saved");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
