import java.util.ArrayList;
import java.util.List;
public class Task {

    private List<User> users;
    private String name;
    private TaskCategory category;
    private boolean isComplete = false;
    private String deadline;
    private String customCategory = "";


    public Task() {
        this.name = "";
        this.category = TaskCategory.EVENT;
        this.deadline = "00:00";
        this.users = new ArrayList<>();
    }

    public Task(String name, TaskCategory category, String deadLine) {
        this.name = name;
        this.category = category;
        this.deadline = deadLine;
        
        this.users = new ArrayList<>();
    }

    public Task(Task task) {
        this.name = task.name;
        this.category = task.category;
        this.deadline = task.deadline;
        this.isComplete = task.isComplete;
        this.users = new ArrayList<>();
        for (User user : task.users) {
            this.users.add(new User(user));
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(TaskCategory category) {
         this.category = category;
        if (category == TaskCategory.OTHER) {
            promptForCustomCategory();
        }
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public void setComplete() {
        isComplete = true;
    }

    public void setCustomCategory(String category) {
        this.customCategory = category;
    }

        public void promptForCustomCategory() {
            if (category == TaskCategory.OTHER) {
                java.util.Scanner scanner = new java.util.Scanner(System.in);
                System.out.print("Enter custom category: ");
                customCategory = scanner.nextLine();
            }
        }

    public void setUsers (List<User> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public TaskCategory getCategory() {
        return category;
    }

    public String getDeadline() {
        return deadline;
    }

    public boolean completed() {
        return isComplete;
    }

    public List<User> getUsers() {
        return users;
    }

    public String getCustomCategory() {
        return customCategory;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

}
