package TaskClasses;
import java.util.*;

public class Task {
    private List<User> users;
    private String name;
    private Category category;
    private boolean isComplete = false;
    private deadLine deadline;

    public Task() {
        this.name = "";
        this.category = new Category();
        this.deadline = new deadLine();
        this.users = new ArrayList<>();
    }

    public Task(String name, Category category, deadLine deadline) {
        this.name = name;
        this.category = new Category(category); // copy constructor
        this.deadline = new deadLine(deadline); // copy constructor
        this.users = new ArrayList<>();
    }

    public Task(Task task) {
        this.name = task.name;
        this.category = new Category(task.category);
        this.deadline = new deadLine(task.deadline);
        this.isComplete = task.isComplete;
        this.users = new ArrayList<>();
        for (User user : task.users) {
            this.users.add(new User(user));
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(Category category) {
        this.category = new Category(category);
    }

    public void setDeadline(deadLine deadline) {
        this.deadline = new deadLine(deadline);
    }

    public void setComplete() {
        isComplete = true;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public deadLine getDeadline() {
        return deadline;
    }

    public boolean completed() {
        return isComplete;
    }

    public List<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Task: ").append(name).append("\n");
        sb.append(category).append("\n");
        sb.append("Deadline: ").append(deadline).append("\n");
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
