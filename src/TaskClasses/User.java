/**
 * Represents a user who can be assigned to tasks in the to-do list system.
 * This is a simple wrapper class that stores a user's name and provides
 * basic functionality for user management.
 *
 * @author  Jed Duncan
 * @version Sep 29, 2025
 */
package TaskClasses;
public class User {
    //fields
    private String name; //the user's display name
    private int userId; //database primary key
    //constructors
    public User() { //default constructor - creates user with empty name
        name = "";
        userId = -1;
    }

    public User(String name) { //parameterized constructor - creates user with specified name
        this.name = name;
        this.userId = -1;
    }

    public User(User user) { //copy constructor - creates copy of another user
        this.name = user.name;
        this.userId = -1;
    }

    //setter
    public void setName(String name) { //sets the user's name
        this.name = name;
    }

    public void setUserId(int id) {
        this.userId = id;
    }

    //getter
    public String getName() { //returns the user's name
        return name;
    }

    public int getUserId() {
        return userId;
    }

    //toString method - returns the user's name for display purposes
    @Override
    public String toString() {
        return name;
    }

    public static User parse(String text) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Input text cannot be null or empty");
        }
        
        // Since toString() simply returns the name, we just need to trim and validate
        String name = text.trim();
        
        if (name.isEmpty()) {
            throw new IllegalArgumentException("User name cannot be empty");
        }
        
        return new User(name);
    }
}
