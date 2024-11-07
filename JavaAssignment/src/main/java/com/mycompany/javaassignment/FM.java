package com.mycompany.javaassignment;
//Extend from User

import java.io.*;

public class FM extends User{
    public FM(int userId, String username, String password, Role role) {
        super(userId, username, password, role);
    }
    
    //Saving user
    
//    @Override
//    public void saveUser() {
//        try (
//            BufferedWriter writer = new BufferedWriter(new FileWriter(USERS, true))) {
//            writer.write(userId + "," + username + "," + password + "," + role + "," + active + "\n");
//            System.out.println(username + " was saved");
//            writer.close();
//        } catch (IOException e) {
//            System.out.println("Error: " + e.getMessage());
//        }
//    }
    
    
}
