package com.mycompany.javaassignment;

public class JavaAssignment {
public static void main(String[] args) {
    System.out.println("Hello World!");
        // Create an instance of User
        User adminUser = new User(1, "ad", "adminpass", Role.FM);
        User adminUser1  = new User(1, "adas", "adminpass", Role.FM);
        // Register new users
        User.register(1, "tria", "password1", Role.User);  // Register user 'tria'
        User.register(2, "bob", "password2", Role.FM);      // Register user 'bob'

        // Attempt to log in
        User.login("tria", "password1");  // Successful login for 'tria'
        User.login("bob", "wrongpassword"); // Unsuccessful login attempt for 'bob'
        User.login("bob", "password2");

        // View profile of a user
        User.viewProfile("bob"); // Displays 'alice's profile
        User.activateUser("bob");

        FM fm1 = new FM(1,"q","w",Role.FM);
    }

}