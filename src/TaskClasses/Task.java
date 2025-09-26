package TaskClasses;
import java.util.*;

public class Task {

    //fields
    private List<User> users;
    private String name;
    private Category category;
    private boolean isComplete = false;
    private DeadLine deadline;
    private String description = "No description";

    //constructors
    public Task() { //default
        this.name = "";
        this.category = new Category();
        this.deadline = new DeadLine();
        this.users = new ArrayList<>();
    }

    public Task(String name, Category category, DeadLine deadline) { //parameterized
        this.name = name;
        this.category = new Category(category); 
        this.deadline = new DeadLine(deadline); 
        this.users = new ArrayList<>();
    }

    public Task(Task task) { //copy
        this.name = task.name;
        this.category = new Category(task.category);
        this.deadline = new DeadLine(task.deadline);
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
        sb.append("Deadline: ").append(deadline).append("\n");
        sb.append("Description: ").append(description).append("\n");
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
