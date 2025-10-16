import TaskClasses.*;
import DateClasses.Date;
import TimeClasses.*;
import java.util.*;
/**
 * Console-based user interface for interacting with the ToDoList application.
 * This class provides methods to display menus, read user input, and perform
 * actions such as adding, removing, and viewing tasks in the to-do list.
 *
 * @author  Jed Duncan
 * @version Oct 6, 2025
 */
public class ToDoListConsoleUI {

    private final TaskDAOInterface dao;
    private final ToDoList toDoList; //the to-do list being managed
    private final Scanner scnr = new Scanner(System.in); //scanner for reading user input

    //constructor
    public ToDoListConsoleUI(TaskDAOInterface dao) {
        this.dao = dao;
        this.toDoList = new ToDoList("My To-Do List"); //initialize with a default name
        List<Task> tasks = dao.findAll();
        this.toDoList.setTasks(tasks);
    }

    public static void main(String[] args) {
        ToDoListConsoleUI ui = new ToDoListConsoleUI(new TaskDAO());
        ui.run();
    }

    public void run() {
        System.out.println("Welcome to the To-Do List Manager!");
        System.out.println("====================================");

        while(true) {
            System.out.println(toDoList);
            displayMenu();
            var choice = getIntInput("Enter your choice (1-7): ", 1, 7);
            switch (choice) {
                case 1 -> 
                    addNewTask(); 
                case 2 ->
                    removeTask(); 
                case 3 ->
                    editTask();
                case 4 ->
                    markTaskComplete();
                case 5 ->
                    applyFilters();
                case 6 ->
                    searchTasks();
                case 7 -> {
                    System.out.println("Exiting the To-Do List Manager. Goodbye!");
                    return; //exit the application
                }
            }
            System.out.println();
        }
    }

    private void displayMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Add new task");
        System.out.println("2. Remove task");
        System.out.println("3. Edit task");
        System.out.println("4. Mark task as complete");
        System.out.println("5. Filter tasks");
        System.out.println("6. Search tasks");
        System.out.println("7. Exit");
    }
    
    private void addNewTask() {
        String title = getStringInput("Enter task title: ");
        String dueDateStr = getStringInput("Enter due date (YYYY-MM-DD): ");
        String dueTimeStr = getStringInput("Enter due time (HH:MM): ");
        Category category = Category.parse(getStringInput("Enter category (Work, Personal, Shopping, Others): "));
        boolean isCompleted = getBooleanInput("Is the task completed?");

        // Parse date and time
        Date dueDate = Date.parse(dueDateStr);
        Time dueTime = Time.parse(dueTimeStr);
        

        Task newTask = new Task(title, category, dueTime, dueDate, isCompleted);
        toDoList.addTask(newTask);
        System.out.println("Task added successfully!");
        Task saved = dao.save(newTask);
        if (saved != null) {
            toDoList.addTask(saved);
        } else {
            System.out.println("Failed to save task to database.");
        }
        //return newTask;
    }

    private void removeTask() {
        isEmpty();
        String title = getStringInput("Enter the title of the task to remove: ");
        Task taskToRemove = toDoList.findTaskByName(title);
        if (taskToRemove != null) {
            toDoList.removeTask(taskToRemove);
            System.out.println("Task removed successfully.");
        } else {
            System.out.println("Task not found.");
        }
        if (taskToRemove != null) {
            boolean ok = dao.delete(taskToRemove.getTaskId());
            if (ok) {
                toDoList.removeTask(taskToRemove);
            } else {
                System.out.println("Failed to delete task from database.");
            }
        }
        //return taskToRemove;
    }

    private void editTask() {
        isEmpty();
        Task taskToEdit = null;
        String title = getStringInput("Enter the name of the task to edit: ");
        if (toDoList.findTaskByName(title) == null) {
            System.out.println("Task not found.");
            String tryAgain = getStringInput("Would you like to try a different task name? 'y' if yes. 'n' to return to menu.");
            if (tryAgain.equalsIgnoreCase("y"))
                editTask();
            else
                return;
        }

        taskToEdit = toDoList.findTaskByName(title);
        System.out.println("Task being edited:\n"+taskToEdit);
        System.out.println("For incoming prompts, press Enter to skip editing the field in question.");
        String newTitle = getStringInput("Enter new title: ");
        String newDescription = getStringInput("Enter new description: ");
        Date newDate = Date.parse(getStringInput("Enter new due date (MM-MM-YYYY): "));
        Time newTime = Time.parse(getStringInput("Enter new due time (HH:MM): "));
        
        Category newCategory = getNewCategory();
        boolean newStatus = getBooleanInput("Is the task completed?");
        
        taskToEdit.editTask(newTitle, newDescription, newTime, newDate, newCategory, newStatus);
        if (!dao.update(taskToEdit)) {
            System.out.println("Failed to update task in database.");
        }
        //return taskToEdit;
    }

    private Category getNewCategory() {
        Category category = null;
        int choice = getIntInput(
                """
                        Select new category:
                        1. School
                        2. Work
                        3. Appointment
                        4. Event
                        5. Chore
                        6. Other
                        Enter choice (1-6):\s""", 1, 6);
        TaskCategory categoryType = switch (choice) {
            case 1 -> TaskCategory.SCHOOL;
            case 2 -> TaskCategory.WORK;
            case 3 -> TaskCategory.APPOINTMENT;
            case 4 -> TaskCategory.EVENT;
            case 5 -> TaskCategory.CHORE;
            case 6 -> TaskCategory.OTHER;
            default -> null;
        };
        category = new Category(categoryType);
        return category;  
    } 


    //marks a task as complete based on user input
    private void markTaskComplete() {
        isEmpty();
        String title = getStringInput("Enter the title of the task to mark as complete: ");
        Task taskToMark = toDoList.findTaskByName(title);
        if (taskToMark != null) {
            taskToMark.setComplete(true);
            System.out.println("Task marked as complete.");
        } else {
            System.out.println("Task not found.");
        }
        if (taskToMark != null) {
            taskToMark.setComplete(true);
            if (!dao.update(taskToMark)) {
                System.out.println("Failed to update task in database.");
            }
        }
        //return taskToMark;
    }

    
    private Task searchTasks() {
        isEmpty();
        Task taskToFind = null;
        String title = getStringInput("Enter the title of the task to search for: ");
        Task foundTask = toDoList.findTaskByName(title);
        if (foundTask != null) {
            System.out.println("Task found:\n" + foundTask);
            taskToFind = foundTask;
        } else {
            System.out.println("Task not found.");
        }
        return taskToFind;
    }

    private List<Task> applyFilters() {
        isEmpty();
        List<Task> results = new ArrayList<Task>();
        int filterType = getIntInput(
            """
            Select filter type:
            1. By Name
            2. By Category
            3. By Date
            4. By Time
            5. By Deadline
            6. By Assigned Users
            7. By Completion Status
            Enter choice (1-7):\s""", 1, 7);
        switch (filterType) {
            case 1 -> {
                String name = getStringInput("Enter name to filter by: ");
                results = toDoList.filterTasks(name, null, null, null, null, null, null);
                System.out.println("Filtered tasks by name:");
                results.forEach(System.out::println);
            }
            case 2 -> {
                Category category = getNewCategory();
                results = toDoList.filterTasks(null, category, null, null, null, null, null);
                System.out.println("Filtered tasks by category:");
                results.forEach(System.out::println);
            }
            case 3 -> {
                Date date = Date.parse(getStringInput("Enter date (MM-MM-YYYY) to filter by: "));
                results = toDoList.filterTasks(null, null, date, null, null, null, null);
                System.out.println("Filtered tasks by date:");
                results.forEach(System.out::println);
            }
            case 4 -> {
                Time time = Time.parse(getStringInput("Enter time (HH:MM) to filter by: "));
                results = toDoList.filterTasks(null, null, null, time, null, null, null);
                System.out.println("Filtered tasks by time:");
                results.forEach(System.out::println);
            }
            case 5 -> {
                DeadLine deadline = DeadLine.parse(getStringInput("Enter deadline (YYYY-MM-DD HH:MM) to filter by: "));
                results = toDoList.filterTasks(null, null, null, null, deadline, null, null);
                System.out.println("Filtered tasks by deadline:");
                results.forEach(System.out::println);
            }
            case 6 -> {
                String usersStr = getStringInput("Enter assigned users (comma-separated) to filter by: ");
                String[] userNames = usersStr.split(",");
                List<User> users = new ArrayList<>();
                for (String name : userNames) {
                    users.add(new User(name.trim()));
                }
                results = toDoList.filterTasks(null, null, null, null, null, users, null);
                System.out.println("Filtered tasks by assigned users:");
                results.forEach(System.out::println);
            }
            case 7 -> {
                boolean isComplete = getBooleanInput("Filter by completed tasks? (y for completed, n for incomplete): ");
                results = toDoList.filterTasks(null, null, null, null, null, null, isComplete);
                System.out.println("Filtered tasks by completion status:"); 
                results.forEach(System.out::println);
            }
        }
        return results;
    }

    //utility method to check if the to-do list is empty before operations
    private void isEmpty() {
        if(toDoList.getTasks().isEmpty()) {
            System.out.println("No tasks to remove.");
            var input = getStringInput("Would you like to add a task? 'y' if yes. 'n' to exit.");
            if (input.equalsIgnoreCase("y")) {
                addNewTask();
            } else {
                System.out.println("Exiting the To-Do List Manager. Goodbye!");
                System.exit(0);
            }
        }
    }

    private String getStringInput(String prompt) {
        System.out.println(prompt);
        return scnr.nextLine();
    }

    // //overloaded method for potential future use
    // private int getIntInput(String prompt) {
    //     while (true) {
    //         try {
    //             System.out.println(prompt);
    //             return Integer.parseInt(scnr.nextLine().trim());
    //         } catch (NumberFormatException e) {
    //             System.out.println("Invalid input. Please enter a valid number.");
    //         }
    //     }
    // }

    private int getIntInput(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.println(prompt);
                int value = Integer.parseInt(scnr.nextLine().trim());
            if (value >= min && value <= max) {
                return value;
            }
            } catch (NumberFormatException e) {
            System.out.println("Input must be between " + min + " and " + max + ". Please try again.");
    
            }
        }
    }

    private boolean getBooleanInput(String prompt) {
        System.out.println(prompt + " (y/n): ");
        String input = scnr.nextLine().trim().toLowerCase();
        while (!input.equals("y") && !input.equals("n")) {
            System.out.println("Invalid input. Please enter 'y' or 'n'.");
            input = scnr.nextLine().trim().toLowerCase();
        }
        return input.equals("y");
    }
}
