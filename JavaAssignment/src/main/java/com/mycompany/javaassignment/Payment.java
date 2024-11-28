
package com.mycompany.javaassignment;


class Payment {
   
    private String paymentNo;
    private String poNo;
    private double amount;
    private String itemNo;
    private String supplierID;
    private String paymentDate;
    private String paymentStatus;

    
    //Constructor
    public Payment(String paymentNo, String poNo, double amount, String itemNo, String supplierID, String paymentDate, String paymentStatus) {
        this.paymentNo = paymentNo;
        this.poNo = poNo;
        this.amount = amount;
        this.itemNo = itemNo;
        this.supplierID = supplierID;
        this.paymentDate = paymentDate;
        this.paymentStatus = paymentStatus;
    }

    
    //Getter
    public String getPaymentNo() {
        return paymentNo;
    }

    public String getPoNo() {
        return poNo;
    }

    public double getAmount() {
        return amount;
    }

    public String getItemNo() {
        return itemNo;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    
    //Setter
    public void setPaymentNo(String paymentNo) {
        this.paymentNo = paymentNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    
    
    // Methods for managing payment records
    public void addPayment() {
    }

    public void paymentHistory() {
    }

    public void editPayment() {
    }

    public void deletePayment() {
    }
    
    @Override
    public String toString() {
        return "Payment{" +
                "paymentNo='" + paymentNo + '\'' +
                ", poNo='" + poNo + '\'' +
                ", amount=" + amount +
                ", itemNo='" + itemNo + '\'' +
                ", supplierID='" + supplierID + '\'' +
                ", paymentDate='" + paymentDate + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                '}';
    }
    
}
