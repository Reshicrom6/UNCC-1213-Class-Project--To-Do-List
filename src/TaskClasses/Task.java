/**
 * Represents a single task in the to-do list with comprehensive properties.
 * A Task contains basic information (name, description, category), timing information
 * (date, time, deadline), completion status, and can be assigned to multiple users.
 *
 * @author  Jed Duncan
 * @version Sep 29, 2025
 */
package TaskClasses;
import java.util.*;
import DateClasses.Date;
import TimeClasses.Time;
public class Task {

    //fields
    private List<User> users; //list of users assigned to this task
    private String name; //the task title/name
    private Category category; //task category (predefined or custom)
    private boolean isComplete = false; //completion status of the task
    private DeadLine deadline = null; //optional deadline for task completion
    private Time time; //time associated with the task
    private Date date; //date associated with the task
    private String description = ""; //detailed description of the task

    //constructors
    public Task() { //default constructor - creates empty task with default values
        this.name = "";
        this.category = new Category();
        this.users = new ArrayList<>();
        this.time = new Time();
        this.date = new Date();
    }

    public Task(String name, Category category, Time time, Date date) { //parameterized constructor - creates task with specified basic properties
        this.name = name;
        this.category = new Category(category); //create copy to ensure encapsulation
        this.time = new Time(time); //create copy to ensure encapsulation
        this.date = new Date(date); //create copy to ensure encapsulation
        this.users = new ArrayList<>();
    }

    public Task(Task task) { //copy constructor - creates deep copy of another task
        this.name = task.name;
        this.category = new Category(task.category);
        this.deadline = new DeadLine(task.deadline);
        this.time = new Time(task.time);
        this.date = new Date(task.date);
        this.isComplete = task.isComplete;
        this.users = new ArrayList<>();
        for (User user : task.users) { //deep copy all users
            this.users.add(new User(user));
        }
    }

    //setters
    public void setName(String name) { //sets the task name
        this.name = name;
    }

    public void setDescription(String description) { //sets the task description
        this.description = description;
    }

    public void setCategory(Category category) { //sets the task category (creates defensive copy)
        this.category = new Category(category);
    }

    public void setDeadline(DeadLine deadline) { //sets the task deadline (creates defensive copy)
        this.deadline = new DeadLine(deadline);
    }

    public void settime(Time time) { //sets the task time (creates defensive copy)
        this.time = new Time(time);
    }

    public void setDate(Date date) { //sets the task date (creates defensive copy)
        this.date = new Date(date);
    }

    public void setComplete() { //marks the task as complete
        isComplete = true;
    }

    public void setUsers(List<User> users) { //sets the list of assigned users
        this.users = users;
    }

    //getters
    public String getName() { //returns the task name
        return name;
    }

    public String getDescription() { //returns the task description
        return description;
    }

    public Category getCategory() { //returns the task category
        return category;
    }

    public DeadLine getDeadline() { //returns the task deadline (may be null)
        return deadline;
    }

    public Date getDate() { //returns the task date
        return date;
    }
    
    public Time getTime() { //returns the task time
        return time;
    }

    public boolean completed() { //returns true if task is marked as complete
        return isComplete;
    }

    public List<User> getUsers() { //returns the list of assigned users
        return users;
    }

    //user management methods
    public void addUser(User user) { //adds a user to the task's assigned user list
        users.add(user);
    }

    public void removeUser(User user) { //removes a user from the task's assigned user list
        users.remove(user);
    }

    //toString method - returns formatted string representation of the complete task
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Task: ").append(name).append("\n");
        sb.append(category).append("\n");
        sb.append("Date: ").append(date).append("\n");
        sb.append("Time: ").append(time).append("\n");
        if (deadline != null) {
            sb.append("Deadline: ").append(deadline).append("\n");
        }
        if (description != null && !description.isEmpty()) {
            sb.append("Description: ").append(description).append("\n");
        }
        sb.append("Status: ").append(isComplete ? "Complete" : "Incomplete").append("\n");
        if (users != null && !users.isEmpty()) {
            sb.append("Users: ");
            for (User user : users) {
                sb.append(user).append(", ");
            }
            sb.setLength(sb.length() - 2); // Remove last comma
            sb.append("\n");
        }
        return sb.toString();
    }
}
