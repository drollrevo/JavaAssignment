//package com.mycompany.javaassignment.Class;
//
//import com.mycompany.javaassignment.Class.Role;
//import com.mycompany.javaassignment.Class.PurchaseManager;
//import com.mycompany.javaassignment.Class.PurchaseOrder;
//import com.mycompany.javaassignment.Class.FM;
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class JavaAssignment {
//    public static void main(String[] args) throws IOException {
//
////         // Create instances of Users (FM and PurchaseManager)
////        FM financeManager = new FM(1, "fm_user", "12345", Role.FM);
////        PurchaseManager purchaseManager = new PurchaseManager(2, "pm_user", "54321", Role.PurchaseManager);
////        
////        //Create list of items
////        List<Item>itemList=new ArrayList<>();
////        itemList.add(new Item("I001", "Chocolate Bar", "SUP001", "Available", 5.00));
////        itemList.add(new Item("I002", "Chocolate Bar", "SUP002", "Available", 3.50));
////        
////        //Create list of supplier
////        List<Supplier>supplierList=new ArrayList<>();
////        supplierList.add(new Supplier("SUP001", "ABC Supplies", "Selangor", "Active", "ITM001", "Item A", 50.0));
////        supplierList.add(new Supplier("SUP002", "XYZ Supplies", "Seremban", "Active", "ITM002", "Item B", 75.0));
////        supplierList.add(new Supplier("SUP003", "123 Supplies", "Bukit Jalil", "Active", "ITM003", "Item C", 100.0));
////
////        //Display list of items
////        System.out.println("\nViewing the list of Items");
////        purchaseManager.viewItems(itemList);
////        
////        //Display list of suppliers
////        System.out.println("\nViewing the list of Suppliers");
////        purchaseManager.viewSuppliers(supplierList);
////        
////        // Create some test items and suppliers
////        Item item1 = new Item("I001", "Chocolate Bar", "SUP001", "Available", 5.00);
////        Item item2 = new Item("I002", "Candy", "SUP002", "Available", 3.50);
////        
////        //creste some test suppliers
////        Supplier supplier1 = new Supplier("SUP001", "ABC Supplies", "Selangor", "Active", "ITM001", "Item A", 50.0);
////        Supplier supplier2 = new Supplier("SUP002", "XYZ Supplies", "Seremban", "Active", "ITM002", "Item B", 75.0);
////        Supplier supplier3 = new Supplier("SUP003", "123 Supplies", "Bukit Jalil", "Active", "ITM003", "Item C", 100.0);
////
////        // Create Purchase Orders
////        PurchaseOrder po1 = new PurchaseOrder("PO001", item1, 100, "2024-11-01", "2024-11-05", 
////                                              "2024-11-07", supplier1, "PM001", "Pending", 
////                                              5.00, 500.00, "Urgent Order");
////
////        PurchaseOrder po2 = new PurchaseOrder("PO002", item2, 100, "2024-12-01", "2024-12-05", 
////                                              "2024-12-07", supplier2, "PM002", "Pending", 
////                                              4.00, 400.00, "Urgent Order");
////
////        // Add Purchase Orders to PurchaseManager
////        purchaseManager.addPurchaseOrder(po1);
////        financeManager.addPurchaseOrder(po1);
////        
////        purchaseManager.addPurchaseOrder(po2);
////        financeManager.addPurchaseOrder(po2);
////
////       // Test Viewing Purchase Orders via FinanceManager (FM)
////        System.out.println("\nFinance Manager Viewing Purchase Orders:");
////        financeManager.viewPurchaseOrders();
////        
////        //Viewing Purchase Orders via PurchaseManager 
////        System.out.println("\nPurchase Manager Viewing Purchase Orders:");
////        purchaseManager.viewPurchaseOrders();
////        
////        //PurchaseManager updating Purchase Orders
////        System.out.println("\nPurchase Manager Editing and Updating Purchase Orders:");
////        purchaseManager.editPurchaseOrder("PO001", "Approved", 150, "Updated quantity and status");
////        
////        //Viewing updated Purchase Orders
////        System.out.println("\nViewing all Purchase Orders after update:");
////        financeManager.viewPurchaseOrders();
////        
////        //Deleting a Purchase Order by PurchaseManager
////        purchaseManager.deletePurchaseOrder("PO002");
////        
////        // View after deletion
////        System.out.println("\nPurchase Manager ~ Viewing all Purchase Orders after delete:");
////        purchaseManager.viewPurchaseOrders();
////
////        // Save Purchase Orders (Testing save functionality)
////        System.out.println("\nSaving Purchase Orders to file...");
////        purchaseManager.savePurchaseOrders();
////        
////        // Edit supplier
////        System.out.println("\nEditing Supplier SUP001...");
////        Supplier.editSupplier("SUP001", "New ABC Supplies", "789 Maple St", "Inactive", "ITM003", "Item C", 60.0);
////
////        // Delete supplier
////        System.out.println("\nDeleting Supplier SUP002...");
////        Supplier.deleteSupplier("SUP002");
////
////        // Display suppliers
////        System.out.println("\nView supplier list:");
////        Supplier.supplierList();
////
////        // Display logs
////        System.out.println("\nDisplaying supplier logs:");
////        Supplier.displayLogs();
//        
//        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                System.out.println(line); // Print each line (user) from the file
//            }
//        } catch (IOException e) {
//            System.err.println("An error occurred while reading the file: " + e.getMessage());
//        }
//    }
//}
//
//
////    System.out.println("Hello World!");
////        // Create an instance of User
////        //User adminUser = new User(1, "ad", "adminpass", Role.FM);
////        //User adminUser1  = new User(1, "adas", "adminpass", Role.FM);
////        // Register new users
////        //User.register(1, "tria", "password1", Role.User);  // Register user 'tria'
////        //User.register(2, "hu", "password2", Role.FM);      // Register user 'bob'
////
////        // Attempt to log in
////        User.login("tria", "password1");  // Successful login for 'tria'
////        User.login("bob", "wrongpassword"); // Unsuccessful login attempt for 'bob'
////        User.login("bob", "password2");
////
////        // View profile of a user
////        User.viewProfile("tria"); // Displays 'alice's profile
////        //User.activateUser("bob");
////
////        FM fm1 = new FM(1,"q","w",Role.FM);
////        PurchaseManager pm1 = new PurchaseManager(2,"a","s",Role.PurchaseManager);
////                
////        Admin admin = new Admin(1, "admin", "adminPass", Role.Admin);
////
////        // Admin registers a new user
////        admin.register(2, "user1", "pass123", Role.FM);
////
////        // Admin activates or deactivates a user
////        admin.deactivateUser("user1");
////        User.viewProfile("user1");
////        admin.activateUser("user1");
////        User.viewProfile("user1");
////        admin.editUser(2, "user1", "2", Role.FM, true);
////        User.viewProfile("user1");
////
////        // Register a new Finance Manager (FM)
////        admin.register(3, "fmuser", "fm123", Role.FM);
////
////        // Register a new Regular User
////        //admin.register(4, "regularUser", "regpass", Role.Admin);
////    
////        //FM actions
//////        fm1.viewPurchaseOrders();
//////        fm1.verifyPurchaseOrder("PO123", "APPROVED");
//////        
//////        //Example of user login;
//////        User.login("user1", "newspass");
//////        
//////        //Admin registers another FM
//////        admin.register(3, "fmuser", "fm123", Role.FM);
////
////          PurchaseOrder p01 = new PurchaseOrder("P0123", "Pending");
////          PurchaseOrder p02 = new PurchaseOrder("P0124", "Apprived");
////          
////          fm1.addPurchaseOrder(p01);
////          
//
//
//
