package com.mycompany.javaassignment.Class;
//Package for reading and writing data to files

import java.io.*;
import javax.swing.JOptionPane;
//All possible role options

public class User {

    private String userId;
    private String username;
    private String password;
    private String role;
    private String active;

    static final String USERS = System.getProperty("user.dir") + "/src/main/java/com/mycompany/javaassignment/Database/users.txt";  //Stores users
    static final String LOGIN = System.getProperty("user.dir") + "/src/main/java/com/mycompany/javaassignment/Database/CurrentUser.txt";  //Stores login users

    //initialize file and if the file does not existthen dynamically create it
    static {
        try {
            File file = new File(USERS);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error initializing USERS file: " + e.getMessage());
        }
    }

    public User() {

    }

    //Constructor
    // Constructor with default active = true
    public User(String userId, String username, String password, String role) {
        this(userId, username, password, role, "ACTIVE"); // Call the extended constructor
    }

    // Constructor with explicit active status
    public User(String userId, String username, String password, String role, String active) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.active = active;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String isActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    //Saving user
    private synchronized void saveUser() {
        try (
                BufferedWriter writer = new BufferedWriter(new FileWriter(USERS, true))) {
            writer.write(userId + "," + username + "," + password + "," + role + "," + active + "\n");
            System.out.println(username + " was saved");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving user to file: " + e.getMessage());
        }
    }

    //Login method
    public String login(String username, String password) {
        try (
                BufferedReader reader = new BufferedReader(new FileReader(USERS))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData[1].equals(username) && userData[2].equals(password) && userData[4].trim().equals("ACTIVE")) {
                    System.out.println("Login successful! Role: " + userData[3]);

                    BufferedWriter bw = new BufferedWriter(new FileWriter(LOGIN, false));
                    String loginLine = String.join(",", userData[0], userData[1], userData[3]);

                    bw.write(loginLine);

                    JOptionPane.showMessageDialog(null, "Login Successful. Welcome!");

                    bw.close();
                    return userData[3];
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    public void logout() {
        clearCurrentUser();
        JOptionPane.showMessageDialog(null, "Logout Successful. Good Bye!");
    }

    public void clearCurrentUser() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(LOGIN, false))) {

            bw.write("");

            bw.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to open CurrentUser.txt");
        }
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

    public String getCurrentUserRole() {
        try (BufferedReader br = new BufferedReader(new FileReader(LOGIN))) {
            String[] split = br.readLine().split(",");

            br.close();
            if (!split[2].isEmpty()) {
                return split[2];
            } else {
                throw new IOException();
            }
        } catch (IOException e) {
            return null;
        }
    }

    public String getCurrentUserID() {
        try (BufferedReader br = new BufferedReader(new FileReader(LOGIN))) {
            String[] split = br.readLine().split(",");

            br.close();
            if (!split[0].isEmpty()) {
                return split[0];
            } else {
                throw new IOException();
            }
        } catch (IOException e) {
            return null;
        }
    }
}
