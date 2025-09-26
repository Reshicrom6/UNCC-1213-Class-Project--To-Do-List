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
    
    public ToDoList(String name) {//parameterized
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

    //task editing method
    public boolean editTask(int index, String newName, String newDescription, DeadLine newDeadLine, Category newCategory) {
        if (index < 0 || index >= tasks.size()) {
            return false;
        }
        Task task = tasks.get(index);
        if (newName != null && !newName.isEmpty()) {
            task.setName(newName);
        }
        if (newDescription != null && !newDescription.isEmpty()) {
            task.setDescription(newDescription);
        }
        if (newDeadLine != null) {
            task.setDeadline(newDeadLine);
        }
        if (newCategory != null) {
            task.setCategory(newCategory);
        }
        return true;
    }

    //Task searching method, searches by task object
    public Task findTask(Task task) {
        if (tasks.contains(task)) {
            return task;
        }
        return null; //task not found
    }

    //Task searching method, searches by task name field
    public Task findTaskByName(String name) {
        for (Task task : tasks) {
            if (task.getName().equalsIgnoreCase(name)) {
                return task;
            }
        }
        return null; //task not found
    }

    //task filtering method
    public List<Task> filterTasks(String name, Category category, DeadLine deadline, Boolean complete) {
        List<Task> result = new ArrayList<>();

        //iterate through all tasks
        for (Task task : tasks) {
            boolean matches = true;

            if (name != null && !name.isEmpty()) { //checks if name is a filter
                if (!task.getName().toLowerCase().contains(name.toLowerCase())) { //checks if passed name argument and current task match
                    matches = false; //if current task and passed string do not match, matches = false
                }
            }
            //repeat procress for all other arguments

            if (category != null) {
                if (!task.getCategory().equals(category)) {
                    matches = false;
                }
            }

            if (deadline != null) {
                if (!task.getDeadline().equals(deadline)) {
                    matches = false;
                }
            }

            if (complete != null) {
                if (task.completed() != complete) {
                    matches = false;
                }
            }

            //if at the end of the loop iteration matches is still true, add the task to the result list
            if (matches) {
                result.add(task);
            }
        }
        return result;
    }
    
    /**
     * Convinced that these are unecessary, since they just call the filter method anyways. seems like a waste of space. The only reason why I thought to add them is because when I
     * shot this project through ChatGPT to ask it for more ideas for functionality, it shoved these into my face. I don't know what purpose they serve though.
    */
    public List<Task> getCompletedTasks() {
        return filterTasks(null, null, null, true);
    }

    public List<Task> getIncompleteTasks() {
        return filterTasks(null, null, null, false);
    }

    public List<Task> getTaskByCategory(Category category) {
        return filterTasks(null, category, null, null);
    }

    public List<Task> getTaskByDeadLine(DeadLine deadLine) {
        return filterTasks(null, null, deadLine, null);
    }

    public List<Task> getTaskByName(String name) {
        return filterTasks(name, null, null, null);
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
