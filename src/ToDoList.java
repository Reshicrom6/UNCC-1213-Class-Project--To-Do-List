import java.util.*;

import TaskClasses.*;
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
        this.tasks = new ArrayList<>();
        for (Task task : list.tasks) {
            this.tasks.add(new Task(task)); // deep copy
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = new ArrayList<>();
        for (Task task : tasks) {
            this.tasks.add(new Task(task)); // deep copy
        }
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public String getName() {
        return name;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(Task task){
        tasks.remove(task);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("To-Do List: ").append(name).append("\n");
        for (Task task : tasks) {
            sb.append(task.toString()).append("\n");
        }
        return sb.toString();
    }
}
