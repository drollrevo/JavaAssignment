package com.mycompany.javaassignment;

public class JavaAssignment {
public static void main(String[] args) {
    System.out.println("Hello World!");
        // Create an instance of User
        //User adminUser = new User(1, "ad", "adminpass", Role.FM);
        //User adminUser1  = new User(1, "adas", "adminpass", Role.FM);
        // Register new users
        //User.register(1, "tria", "password1", Role.User);  // Register user 'tria'
        //User.register(2, "hu", "password2", Role.FM);      // Register user 'bob'

        // Attempt to log in
        User.login("tria", "password1");  // Successful login for 'tria'
        User.login("bob", "wrongpassword"); // Unsuccessful login attempt for 'bob'
        User.login("bob", "password2");

        // View profile of a user
        User.viewProfile("tria"); // Displays 'alice's profile
        //User.activateUser("bob");

        FM fm1 = new FM(1,"q","w",Role.FM);
                
        Admin admin = new Admin(1, "admin", "adminPass", Role.Admin);

        // Admin registers a new user
        admin.register(2, "user1", "pass123", Role.FM);

        // Admin activates or deactivates a user
        admin.deactivateUser("user1");
        User.viewProfile("user1");
        admin.activateUser("user1");
        User.viewProfile("user1");
        admin.editUser(2, "user1", "2", Role.FM, true);
        User.viewProfile("user1");

        // Register a new Finance Manager (FM)
        admin.register(3, "fmuser", "fm123", Role.FM);

        // Register a new Regular User
        admin.register(4, "regularUser", "regpass", Role.Admin);
    }

}