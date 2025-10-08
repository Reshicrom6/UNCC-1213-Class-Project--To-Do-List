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
    private boolean isComplete; //completion status of the task
    private DeadLine deadline = null; //optional deadline for task completion
    private Time time; //time associated with the task
    private Date date; //date associated with the task
    private String description = ""; //detailed description of the task
    private int taskId; //database primary key
    private static int numberOfTasks = 0; //static counter to track number of Task instances

    //constructors
    public Task() { //default constructor - creates empty task with default values
        this.taskId = -1;
        this.name = "";
        this.category = new Category();
        this.users = new ArrayList<>();
        this.time = new Time();
        this.date = new Date();
        this.isComplete = false;
        numberOfTasks++;
    }

    public Task(String name, Category category, Time time, Date date) { //parameterized constructor - creates task with specified basic properties
        this.taskId = -1;
        this.name = name;
        this.category = new Category(category); //create copy to ensure encapsulation
        this.time = new Time(time); //create copy to ensure encapsulation
        this.date = new Date(date); //create copy to ensure encapsulation
        this.isComplete = false;
        this.users = new ArrayList<>();
        numberOfTasks++;
    }

    public Task(String name, Category category, Time time, Date date, boolean isComplete) { //parameterized constructor - creates task with specified basic properties
        this.taskId = -1;
        this.name = name;
        this.category = new Category(category); //create copy to ensure encapsulation
        this.time = new Time(time); //create copy to ensure encapsulation
        this.date = new Date(date); //create copy to ensure encapsulation
        this.isComplete = isComplete;
        this.users = new ArrayList<>();
        numberOfTasks++;
    }

    public Task(Task task) { //copy constructor - creates deep copy of another task
        this.taskId = -1;
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
        numberOfTasks++;
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

    public void setTaskId(int taskId) { //sets task id field
        this.taskId = taskId;
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

    public int getTaskId() { //returns taskId field
        return taskId;
    }

    public static int getNumberOfTasks() {
        return numberOfTasks;
    }
    
    public boolean isSavedToDatabase() {
        return taskId > 0;
    }

    //user management methods
    public void addUser(User user) { //adds a user to the task's assigned user list
        users.add(user);
    }

    public void removeUser(User user) { //removes a user from the task's assigned user list
        users.remove(user);
    }

    public void editUsers(int choice, User user) {
        if (choice == 1) {
            addUser(user);
        } else if (choice == 2) {
            removeUser(user);
        }
    }

    //toString method - returns formatted string representation of the complete task
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

    public static Task parse(String text) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Text cannot be null or empty");
        }

        text = text.trim();
        String[] lines = text.split("\n");
        
        if (lines.length < 4) {
            throw new IllegalArgumentException("Invalid task format - missing required fields");
        }

        try {
            // Parse Task name
            if (!lines[0].startsWith("Task: ")) {
                throw new IllegalArgumentException("Invalid task name format");
            }
            String name = lines[0].substring(6);

            // Parse Category
            Category category = Category.parse(lines[1]);

            // Parse Date
            if (!lines[2].startsWith("Date: ")) {
                throw new IllegalArgumentException("Invalid date format");
            }
            Date date = Date.parse(lines[2].substring(6));

            // Parse Time
            if (!lines[3].startsWith("Time: ")) {
                throw new IllegalArgumentException("Invalid time format");
            }
            Time time = Time.parse(lines[3].substring(6));

            // Create task with basic info
            Task task = new Task(name, category, time, date);

            // Parse optional fields
            for (int i = 4; i < lines.length; i++) {
                String line = lines[i];
                
                if (line.startsWith("Deadline: ")) {
                    task.setDeadline(DeadLine.parse(line.substring(10)));
                } else if (line.startsWith("Description: ")) {
                    task.setDescription(line.substring(13));
                } else if (line.startsWith("Status: ")) {
                    String status = line.substring(8);
                    if ("Complete".equals(status)) {
                        task.setComplete();
                    }
                } else if (line.startsWith("Users: ")) {
                    String usersStr = line.substring(7);
                    String[] userStrings = usersStr.split(", ");
                    List<User> users = new ArrayList<>();
                    for (String userStr : userStrings) {
                        users.add(User.parse(userStr.trim()));
                    }
                    task.setUsers(users);
                }
            }

            return task;

        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse task: " + e.getMessage(), e);
        }
    }
}
