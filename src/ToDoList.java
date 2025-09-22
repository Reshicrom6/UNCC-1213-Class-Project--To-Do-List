import java.util.ArrayList;
import java.util.List;
public class ToDoList {

    private String name;
    private List<Task> tasks;


    public ToDoList() {
        this.name = "To-Do List";
        this.tasks = new ArrayList<>();
    }
    
    public ToDoList(String name) {
        this.name = name;
        this.tasks = new ArrayList<>();
    }

    public ToDoList(ToDoList list) {
        this.name = list.name;
        this.tasks = new ArrayList<>(list.tasks);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public String getName() {
        return name;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public void removeTask(Task task){
        this.tasks.remove(task);
    }


}
