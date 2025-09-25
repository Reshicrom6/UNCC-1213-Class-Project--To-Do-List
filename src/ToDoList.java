import java.util.*;

import TaskClasses.*;

public class ToDoList {

    //fields
    private String name;
    private List<Task> tasks;

    //constructors
    public ToDoList() { //default
        this.name = "To-Do List";
        this.tasks = new ArrayList<>();
    }
    
    public ToDoList(String name) {
        this.name = name;
        this.tasks = new ArrayList<>();
    }

    public ToDoList(ToDoList list) { //copy
        this.name = list.name;
        this.tasks = new ArrayList<>();
        for (Task task : list.tasks) {
            this.tasks.add(new Task(task));
        }
    }

    //setters
    public void setName(String name) {
        this.name = name;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = new ArrayList<>();
        for (Task task : tasks) {
            this.tasks.add(new Task(task));
        }
    }

    //getters
    public List<Task> getTasks() {
        return tasks;
    }

    public String getName() {
        return name;
    }

    //adds a task to list
    public void addTask(Task task) {
        tasks.add(task);
    }
    
    //removes a task from list
    public void removeTask(Task task) {
        tasks.remove(task);
    }

    //toString method
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("To-Do List: ").append(name).append("\n");
        for (Task task : tasks) {
            sb.append(task.toString()).append("\n");
        }
        return sb.toString();
    }
}
