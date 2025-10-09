/**
 * Main ToDoList class that manages a collection of tasks with comprehensive functionality.
 * This class provides task management operations including adding, removing, editing,
 * searching, and filtering tasks. It supports various filter criteria and provides
 * convenient methods for common task operations.
 *
 * @author  Jed Duncan
 * @version Sep 29, 2025
 */
import java.util.*;

import TaskClasses.*;
import DateClasses.Date;
import TimeClasses.Time;
public class ToDoList {

    //fields
    private String name;      //the display name of this to-do list
    private List<Task> tasks; //collection of all tasks in this list

    //constructors
    public ToDoList() { //default constructor - creates empty list with default name
        this.name = "To-Do List";
        this.tasks = new ArrayList<>();
    }
    
    public ToDoList(String name) { //parameterized constructor - creates empty list with custom name
        this.name = name;
        this.tasks = new ArrayList<>();
    }

    public ToDoList(ToDoList list) { //copy constructor - creates deep copy of another list
        this.name = list.name;
        this.tasks = new ArrayList<>();
        for (Task task : list.tasks) {
            this.tasks.add(new Task(task)); //create deep copy of each task
        }
    }

    //setters
    public void setName(String name) { //sets the display name of the to-do list
        this.name = name;
    }

    public void setTasks(List<Task> tasks) { //replaces all tasks with deep copies of provided tasks
        this.tasks = new ArrayList<>();
        for (Task task : tasks) {
            this.tasks.add(new Task(task)); //create defensive copies
        }
    }

    //getters
    public List<Task> getTasks() { //returns the list of tasks (direct reference for efficiency)
        return tasks;
    }

    public String getName() { //returns the display name of the to-do list
        return name;
    }

    //task management methods
    public Task addTask(Task task) { //adds a task to the list, returns the added task
        tasks.add(task);
        return task;
    }
    
    public Task removeTask(Task task) { //removes a task from the list, returns the removed task
        tasks.remove(task);
        return task;
    }

    //task editing method - updates task properties at specified index, returns true if successful
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

    //task searching methods
    public Task findTask(Task task) { //searches for a task by object equality
        if (tasks.contains(task)) {
            return task;
        }
        return null; //task not found
    }

    public Task findTaskByName(String name) { //searches for a task by name (case-insensitive)
        for (Task task : tasks) {
            if (task.getName().equalsIgnoreCase(name)) {
                return task;
            }
        }
        return null; //task not found
    }

    //task filtering method - returns tasks matching ALL provided criteria (null parameters are ignored)
    public List<Task> filterTasks(String name, Category category, Date date, Time time, DeadLine deadline,  List<User> users, Boolean complete) {
        List<Task> result = new ArrayList<>();

        //determine which filters are active (null or empty parameters are ignored)
        boolean filterByName = name != null && !name.isEmpty();
        boolean filterByCategory = category != null;
        boolean filterByDate = date != null;
        boolean filterByTime = time != null;
        boolean filterByDeadline = deadline != null;
        boolean filterByComplete = complete != null;
        boolean filterByUsers = users != null;

        //iterate through all tasks and check each filter condition
        for (Task task : tasks) {
            boolean matches = true;
            
            //name filter: check if task name contains the search string (case-insensitive)
            if (filterByName && !task.getName().toLowerCase().contains(name.toLowerCase())) {
                matches = false;
            }
            
            //category filter: check for exact category match
            if (filterByCategory && !task.getCategory().equals(category)) {
                matches = false;
            }

            //date filter: check for exact date match
            if (filterByDate && !task.getDate().equals(date)) {
                matches = false;
            }

            //time filter: check for exact time match
            if (filterByTime && !task.getTime().equals(time)) {
                matches = false;
            }

            //deadline filter: check for exact deadline match
            if (filterByDeadline && !task.getDeadline().equals(deadline)) {
                matches = false;
            }

            //completion status filter: check if completion status matches
            if (filterByComplete && task.completed() != complete) {
                matches = false;
            }

            //user filter: check if task contains ALL specified users
            if (filterByUsers) {
                for (User user : users) {
                    if (!task.getUsers().contains(user)) {
                        matches = false;
                        break;
                    }
                }
            }
            
            //if task passes all active filters, add it to results
            if (matches) {
                result.add(task);
            }
        }
        return result;
    }
    
    //convenience filter methods - provide simple single-criteria filtering
    //these methods offer a more intuitive API for common filtering operations
    
    public List<Task> getCompletedTasks() { //returns all completed tasks
        return filterTasks(null, null, null, null, null, null, true);
    }

    public List<Task> getIncompleteTasks() { //returns all incomplete tasks
        return filterTasks(null, null, null, null, null, null, false);
    }

    public List<Task> getTaskByCategory(Category category) { //returns all tasks in specified category
        return filterTasks(null, category, null, null, null, null, null);
    }

    public List<Task> getTaskByDate(Date date) { //returns all tasks on specified date
        return filterTasks(null, null, date, null, null, null, null);
    }

    public List<Task> getTaskByTime(Time time) { //returns all tasks at specified time
        return filterTasks(null, null, null, time, null, null, null);
    }

    public List<Task> getTaskByDeadLine(DeadLine deadLine) { //returns all tasks with specified deadline
        return filterTasks(null, null, null, null, deadLine, null, null);
    }

    public List<Task> getTaskByName(String name) { //returns all tasks containing specified name substring
        return filterTasks(name, null, null, null, null, null, null);
    }

    public List<Task> getTaskByUsers(List<User> users) { //returns all tasks assigned to specified users
        return filterTasks(null, null, null, null, null, users, null);
    }

    //toString method - returns formatted string representation of the entire to-do list
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("To-Do List: ").append(name).append("\n");
        for (Task task : tasks) {
            sb.append(task.toString()).append("\n"); //append each task's string representation
        }
        return sb.toString();
    }
}
