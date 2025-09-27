package TaskClasses;
import java.util.*;
import DateClasses.Date;
import TimeClasses.Time;
public class Task {

    //fields
    private List<User> users;
    private String name;
    private Category category;
    private boolean isComplete = false;
    private DeadLine deadline = null;
    private Time time;
    private Date date;
    private String description = "";

    //constructors
    public Task() { //default
        this.name = "";
        this.category = new Category();
        this.users = new ArrayList<>();
        this.time = new Time();
        this.date = new Date();
    }

    public Task(String name, Category category, Time time, Date date) { //parameterized
        this.name = name;
        this.category = new Category(category); 
        this.time = new Time(time);
        this.date = new Date(date);
        this.users = new ArrayList<>();
    }

    public Task(Task task) { //copy
        this.name = task.name;
        this.category = new Category(task.category);
        this.deadline = new DeadLine(task.deadline);
        this.time = new Time(task.time);
        this.date = new Date(task.date);
        this.isComplete = task.isComplete;
        this.users = new ArrayList<>();
        for (User user : task.users) {
            this.users.add(new User(user));
        }
    }

    //setters
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(Category category) {
        this.category = new Category(category);
    }

    public void setDeadline(DeadLine deadline) {
        this.deadline = new DeadLine(deadline);
    }

    public void settime(Time time) {
        this.time = new Time(time);
    }

    public void setDate(Date date) {
        this.date = new Date(date);
    }

    public void setComplete() {
        isComplete = true;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    //getters
    public String getName() {
        return name;
    }

    public String getDestription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public DeadLine getDeadline() {
        return deadline;
    }

    public Date getDate() {
        return date;
    }
    
    public Time getTime() {
        return time;
    }

    public boolean completed() {
        return isComplete;
    }

    public List<User> getUsers() {
        return users;
    }

    //adds a user from the user list
    public void addUser(User user) {
        users.add(user);
    }

    //removes a user from the user list
    public void removeUser(User user) {
        users.remove(user);
    }

    //toString method
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
