package com.mycompany.javaassignment;
//Package for reading and writing data to files
import java.io.*;
//All possible role options
enum Role {
    FM, User
}

public class User {
    private int userId;
    private String username;
    private String password;
    private Role role;
    private boolean active;

    private static final String USERS = "users.txt";  //Stores users

    //Constructor
    public User(int userId, String username, String password, Role role) {
        
        if (usernameTaken(username) == true) {//No same username
            System.out.println("Username already exists. Please choose a different one.");
            return;
        }
        
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.active = true;
        saveUser();
    }
    
    //Register a new user
    public static void register(int userId, String username, String password, Role role) {
       new User(userId, username, password, role); 
    }//Make like this cause when want use "usernameTaken" inside "register" got error about "static etc."

    //Saving user
    private void saveUser() {
        try (
            BufferedWriter writer = new BufferedWriter(new FileWriter(USERS, true))) {
            writer.write(userId + "," + username + "," + password + "," + role + "," + active + "\n");
            System.out.println(username + " was saved");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //Checks is the username exist
    private boolean usernameTaken(String username) {
        try (
            BufferedReader reader = new BufferedReader(new FileReader(USERS))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData[1].equals(username)) {
                    return true;
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }

    //Delete or make inactive
    public static void deactivateUser(String username) {
    updateUserStatus(username, false);
    System.out.println("User " + username + " is now inactive.");
    }
    //Reborn or make active
    public static void activateUser(String username) {
    updateUserStatus(username, true);
    System.out.println("User " + username + " is now active.");
    }

    //Update user status in the file
    private static void updateUserStatus(String username, boolean newStatus) {
    try (
         BufferedReader reader = new BufferedReader(new FileReader(USERS));
         BufferedWriter writer = new BufferedWriter(new FileWriter("temp.txt"))) {

        String line;
        while ((line = reader.readLine()) != null) {
            String[] userData = line.split(",");
            if (userData[1].equals(username)) {
                userData[4] = String.valueOf(newStatus);  //Update active status
                line = String.join(",", userData);        
            }
            writer.write(line + "\n");
        }

        //Replace old file
        new File(USERS).delete();
        new File("temp.txt").renameTo(new File(USERS));
        
        reader.close();
        writer.close();

    } catch (IOException e) {
        System.out.println("Error: " + e.getMessage());
    }
    }

    //Login method
    public static void login(String username, String password) {
        try (
            BufferedReader reader = new BufferedReader(new FileReader(USERS))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData[1].equals(username) && userData[2].equals(password) && userData[4].trim().equals("true")) {
                    System.out.println("Login successful! Role: " + userData[3]);
                    return;
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("Invalid username or password, or user is inactive.");
    }
    
    //View profile data
    public static void viewProfile(String username) {
        try (
            BufferedReader reader = new BufferedReader(new FileReader(USERS))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData[1].equals(username)) {
                    System.out.println("User Profile:");
                    System.out.println("UserID: " + userData[0]);
                    System.out.println("Username: " + userData[1]);
                    System.out.println("Role: " + userData[3]);
                    System.out.println("Active: " + userData[4]);
                    return;
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("User not found.");
    }
}
