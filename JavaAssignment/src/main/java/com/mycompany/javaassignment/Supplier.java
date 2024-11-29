package com.mycompany.javaassignment;

public class Supplier {
    private String supplierID;
    private String supplierName;
    private String address;
    private String supplierStatus;

    public Supplier(String supplierID, String supplierName, String address, String supplierStatus) {
        this.supplierID = supplierID;
        this.supplierName = supplierName;
        this.address = address;
        this.supplierStatus = supplierStatus;
    }

    // Getter methods
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

    @Override
    public String toString() {
        return "Supplier ID: " + supplierID + ", Name: " + supplierName + ", Address: " + address + ", Status: " + supplierStatus;
    }
}
