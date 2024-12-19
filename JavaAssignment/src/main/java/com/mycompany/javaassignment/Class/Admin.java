//package com.mycompany.javaassignment.Class;
//
//import java.io.*;
////Extend from User
//public class Admin extends User{
//    private static final String USERS = "users.txt";  //Stores users
//        
//    public Admin(int userId, String username, String password, Role role) {
//        super(userId, username, password, role);
//    }
//    
//    //Register a new user
////    public static void register(int userId, String username, String password, Role role) {
////       new User(userId, username, password, role); 
////    }//Make like this cause when want use "usernameTaken" inside "register" got error about "static etc."
//
//    // Register method for creating new users
//    
////    public void registerUser(int userId, String username, String password, Role role) {
////        User newUser = new RegularUser(userId, username, password, role);
////        newUser.setActive(true); // Using the setter method
////        newUser.saveUser();
////    }
//    
//    public void register(int userId, String username, String password, Role role) {
//        if (usernameTaken(username)) {
//            System.out.println("Username:" + username + " already exists. Choose another one.");
//            return;
//        }
//
//        User newUser = null;
//        switch (role) {
//            case Admin:
//                newUser = new Admin(userId, username, password, role.Admin);
//                break;
//            case FM:
//                newUser = new FM(userId, username, password, role.FM); // Define FinanceManager as a subclass of User
//                break;
//            //Add other roles
//            default:
//                System.out.println("Invalid role specified.");
//                return;
//        }
//
//        // Set user as active by default
//    if (newUser != null) {
//        newUser.setActive(true);
//        saveUser(newUser);  // Save the new user to the file
//        System.out.println("User " + username + " registered successfully as " + role);
//
//    }
//    
//    }
//    
//    //Saving user
//    
////    @Override
////    public void saveUser() {
////        try (
////            BufferedWriter writer = new BufferedWriter(new FileWriter(USERS, true))) {
////            writer.write(userId + "," + username + "," + password + "," + role + "," + active + "\n");
////            System.out.println(username + " was saved");
////            writer.close();
////        } catch (IOException e) {
////            System.out.println("Error: " + e.getMessage());
////        }
////    }
//
//    public static void saveUser(User user) {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS, true))) {
//            // Write user data to file
//            writer.write(user.getUserId() + "," + user.getUsername() + "," + user.getPassword() + "," + user.getRole() + "," + user.isActive() + "\n");
//            System.out.println(user.getUsername() + " was saved");
//            writer.close();
//        } catch (IOException e) {
//            System.out.println("Error: " + e.getMessage());
//        }
//    }
//    
//    //Checks is the username exist
//    private boolean usernameTaken(String username) {
//        try (
//            BufferedReader reader = new BufferedReader(new FileReader(USERS))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] userData = line.split(",");
//                if (userData[1].equals(username)) {
//                    return true;
//                }
//            }
//            reader.close();
//        } catch (IOException e) {
//            System.out.println("Error: " + e.getMessage());
//        }
//        return false;
//    }
//    
//    //Delete or make inactive
//    public static void deactivateUser(String username) {
//    updateUserStatus(username, false);
//    System.out.println("User " + username + " is now inactive.");
//    }
//    //Reborn or make active
//    public static void activateUser(String username) {
//    updateUserStatus(username, true);
//    System.out.println("User " + username + " is now active.");
//    }
//
//    //Update user status in the file
//    private static void updateUserStatus(String username, boolean newStatus) {
//    try (
//         BufferedReader reader = new BufferedReader(new FileReader(USERS));
//         BufferedWriter writer = new BufferedWriter(new FileWriter("temp.txt"))) {
//
//        String line;
//        while ((line = reader.readLine()) != null) {
//            String[] userData = line.split(",");
//            if (userData[1].equals(username)) {
//                userData[4] = String.valueOf(newStatus);  //Update active status
//                line = String.join(",", userData);        
//            }
//            writer.write(line + "\n");
//        }
//
//        //Replace old file
//        new File(USERS).delete();
//        new File("temp.txt").renameTo(new File(USERS));
//        
//        reader.close();
//        writer.close();
//
//    } catch (IOException e) {
//        System.out.println("Error: " + e.getMessage());
//        }
//    }
//    
//    public void editUser(int userId, String newUsername, String newPassword, Role newRole, boolean newActiveStatus) {
//    try (BufferedReader reader = new BufferedReader(new FileReader(USERS));
//         BufferedWriter writer = new BufferedWriter(new FileWriter("temp.txt"))) {
//        String line;
//        while ((line = reader.readLine()) != null) {
//            String[] userData = line.split(",");
//            int currentUserId = Integer.parseInt(userData[0].trim());
//
//            if (currentUserId == userId) {
//                    if (newUsername != null) {
//                        setUsername(newUsername); 
//                        userData[1] = newUsername;
//                    }
//                    if (newPassword != null) {
//                        setPassword(newPassword);  
//                        userData[2] = newPassword;
//                    }
//                    if (newRole != null) {
//                        setRole(newRole);  
//                        userData[3] = newRole.toString();
//                    }
//                    setActive(newActiveStatus);
//                    userData[4] = String.valueOf(newActiveStatus);
//                    line = String.join(",", userData);
//                }
//                writer.write(line + "\n");
//            }
//
//        new File(USERS).delete();
//        new File("temp.txt").renameTo(new File(USERS));
//
//        System.out.println("User " + userId + " has been edited successfully.");
//        
//        reader.close();
//        writer.close();
//        
//    } catch (IOException e) {
//        System.out.println("Error: " + e.getMessage());
//        }
//    }
//
//}
