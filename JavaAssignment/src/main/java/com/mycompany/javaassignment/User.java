package com.mycompany.javaassignment;
//Package for reading and writing data to files
import java.io.*;
//All possible role options
enum Role {
    FM, Admin
}

public abstract class User {
    private int userId;
    private String username;
    private String password;
    private Role role;
    private boolean active;

    private static final String USERS = "users.txt";  //Stores users

    //Constructor
    public User(int userId, String username, String password, Role role) {
        
//        if (usernameTaken(username) == true) {//No same username
//            System.out.println("Username:" + username + " already exists. Please choose a different one.");
//            return;
//        }
        
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.active = true;
        //saveUser();
    }
    
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    //Saving user
//    private void saveUser() {
//        try (
//            BufferedWriter writer = new BufferedWriter(new FileWriter(USERS, true))) {
//            writer.write(userId + "," + username + "," + password + "," + role + "," + active + "\n");
//            System.out.println(username + " was saved");
//            writer.close();
//        } catch (IOException e) {
//            System.out.println("Error: " + e.getMessage());
//        }
//    }

    //Checks is the username exist
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
