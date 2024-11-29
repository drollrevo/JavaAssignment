package com.mycompany.javaassignment;

public class PurchaseOrder {
    private String poNo;
    private Item item;
    private int purchaseQty;
    private String purchaseDate;
    private String estReceiveDate;
    private String actReceiveDate;
    private Supplier supplier;
    private String pmID;
    private String poStatus;
    private double pricePerUnit;
    private double totalAmount;
    private String orderRemarks;

    // Constructor to initialize PurchaseOrder
    public PurchaseOrder(String poNo, Item item, int purchaseQty, String purchaseDate, String estReceiveDate, 
                         String actReceiveDate, Supplier supplier, String pmID, String poStatus, 
                         double pricePerUnit, double totalAmount, String orderRemarks) {
        this.poNo = poNo;
        this.item = item;
        this.purchaseQty = purchaseQty;
        this.purchaseDate = purchaseDate;
        this.estReceiveDate = estReceiveDate;
        this.actReceiveDate = actReceiveDate;
        this.supplier = supplier;
        this.pmID = pmID;
        this.poStatus = poStatus;
        this.pricePerUnit = pricePerUnit;
        this.totalAmount = totalAmount;
        this.orderRemarks = orderRemarks;
    }

    // Getter methods
    public String getPoNo() {
        return poNo;
    }

    public String getItemNo() {
        return item.getItemNo();
    }

    public String getItemName() {
        return item.getItemName();
    }

    public int getPurchaseQty() {
        return purchaseQty;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public String getEstReceiveDate() {
        return estReceiveDate;
    }

    public String getActReceiveDate() {
        return actReceiveDate;
    }

    public String getSupplierID() {
        return supplier.getSupplierID();
    }

    public String getPmID() {
        return pmID;
    }

    public String getPoStatus() {
        return poStatus;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getOrderRemarks() {
        return orderRemarks;
    }

    // Setter methods
    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public void setPurchaseQty(int purchaseQty) {
        this.purchaseQty = purchaseQty;
    }

    public void setPoStatus(String poStatus) {
        this.poStatus = poStatus;
    }

    public void setOrderRemarks(String orderRemarks) {
        this.orderRemarks = orderRemarks;
    }

    @Override
    public String toString() {
        return "PO No: " + poNo + ", Item: " + item + ", Quantity: " + purchaseQty + ", Purchase Date: " + purchaseDate + 
               ", Supplier: " + supplier + ", Status: " + poStatus + ", Price Per Unit: " + pricePerUnit + 
               ", Total Amount: " + totalAmount + ", Remarks: " + orderRemarks;
    }
}
