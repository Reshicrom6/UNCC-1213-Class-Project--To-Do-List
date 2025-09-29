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

    //constructors
    public User() { //default constructor - creates user with empty name
        name = "";
    }

    public User(String name) { //parameterized constructor - creates user with specified name
        this.name = name;
    }

    public User(User user) { //copy constructor - creates copy of another user
        this.name = user.name;
    }

    //setter
    public void setName(String name) { //sets the user's name
        this.name = name;
    }

    //getter
    public String getName() { //returns the user's name
        return name;
    }

    //toString method - returns the user's name for display purposes
    @Override
    public String toString() {
        return name;
    }
}
