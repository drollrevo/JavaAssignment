package com.mycompany.javaassignment.Class;

import javax.swing.*;
import java.io.*;
import java.util.*;

//Extend from User
public class Admin extends User {

    static final String USERS = System.getProperty("user.dir") + "/src/main/java/com/mycompany/javaassignment/Database/users.txt";  //Stores users
    static final String TEMP = System.getProperty("user.dir") + "/src/main/java/com/mycompany/javaassignment/Database/temp.txt";  //Stores users 

    User user = new User() {
    };

    public Admin() {

    }

    public Admin(String userId, String username, String password, String role) {
        super(userId, username, password, role);
    }

    public Admin(String userId, String username, String password, String role, String active) {
        super(userId, username, password, role, active);
    }

    public void register(String userId, String username, String password, String role, String active) {
        if (usernameTaken(username)) {
            JOptionPane.showMessageDialog(null, "Username:" + username + " already exists. Choose another one.");
            return;
        } else if (useridTaken(userId)) {
            JOptionPane.showMessageDialog(null, "UserID:" + userId + " already exists. Choose another one.");
            return;
        }

        String newUser = String.join(",", new String[]{
            userId, username, password, role, active
        });

        // Set user as active by default
        if (newUser != null) {
            saveUser(newUser);  // Save the new user to the file
            JOptionPane.showMessageDialog(null, "User " + username + " registered successfully as " + role);
        }
    }

    public static void saveUser(String newUser) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS, true))) {
            // Write user data to file
            writer.write(newUser + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //Checks is the username exist
    public boolean usernameTaken(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS))) {
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

    public boolean useridTaken(String userid) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData[0].equals(userid)) {
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
                BufferedReader reader = new BufferedReader(new FileReader(USERS)); BufferedWriter writer = new BufferedWriter(new FileWriter("temp.txt"))) {

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

    public void editUser(String userId, String newUsername, String newPassword, String newRole, String newActiveStatus) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS)); BufferedWriter writer = new BufferedWriter(new FileWriter(TEMP))) {
            String line;
            boolean check = false;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                String currentUserId = userData[0].trim();

                if (currentUserId.equals(userId)) {
                    line = String.join(",", userId, newUsername, newPassword, newRole, newActiveStatus);
                    check = true;
                }

                writer.write(line + "\n");
            }
            reader.close();
            writer.close();

            new File(USERS).delete();
            new File(TEMP).renameTo(new File(USERS));

            if (check) {
                JOptionPane.showMessageDialog(null, "User " + userId + " has been edited successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "Fail to update, please check your userID");
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public List<Admin> adminList() {
        List<Admin> admin = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(USERS))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 5) {
                    continue;
                }
                try {
                    String userId = parts[0].trim();
                    String username = parts[1].trim();
                    String password = parts[2].trim();
                    String role = parts[3].trim();
                    String active = parts[4].trim();
                    admin.add(new Admin(userId, username, password, role, active));
                } catch (Exception e) {
                    System.out.println("Error parsing line: " + line);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading users.txt: " + e.getMessage());
        }
        System.out.println("Users loaded: " + admin.size()); // Debugging output
        return admin;
    }

}
