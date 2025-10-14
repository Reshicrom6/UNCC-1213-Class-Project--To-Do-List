import java.util.Scanner;
import TaskClasses.*;
import DateClasses.*;
import TimeClasses.*;
/**
 * Console-based user interface for interacting with the ToDoList application.
 * This class provides methods to display menus, read user input, and perform
 * actions such as adding, removing, and viewing tasks in the to-do list.
 *
 * @author  Jed Duncan
 * @version Oct 6, 2025
 */
public class ToDoListConsoleUI {

    private ToDoList toDoList; //the to-do list being managed
    private Scanner scnr = new Scanner(System.in); //scanner for reading user input

    //constructor
    public ToDoListConsoleUI() {
        this.toDoList = new ToDoList("My To-Do List"); //initialize with a default name
    }

    public static void main(String[] args) {
        ToDoListConsoleUI ui = new ToDoListConsoleUI();
        ui.run();
    }

    public void run() {
        System.out.println("Welcome to the To-Do List Manager!");
        System.out.println("====================================");

        while(true) {
            System.out.println(toDoList);
            displayMenu();
            var choice = getIntInput("Enter your choice: ", 1, 5);
            switch (choice) {
                case 1:
                    addNewTask();
                    break;
                case 2:
                    removeTask();
                    break;
                case 3:
                    editTask();
                    break;
                case 4:
                    markTaskComplete();
                    break;
                case 5:
                    System.out.println("Exiting the To-Do List Manager. Goodbye!");
                    return; //exit the program
            }
            System.out.println();
        }
    }

    private void displayMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Add new task");
        System.out.println("2. Remove task");
        System.out.println("3. Edit task");
        System.out.println("4. View all tasks");
        System.out.println("5. Mark task as complete");
        System.out.println("6. Search tasks");
        System.out.println("7. Apply filters");
        System.out.println("8. Exit");
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
    }

    private void editTask() {
        isEmpty();
        Task taskToEdit;
        String title = getStringInput("Enter the name of the task to edit: ");
        if (toDoList.findTaskByName(title) == null) {
            System.out.println("Task not found.");
            String tryAgain = getStringInput("Would you like to try a different title? 'y' if yes. 'n' to exit.");
            if (tryAgain.equalsIgnoreCase("y")) {
                editTask();
            } else {
                return;
            }
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
    }

    //
    // private void searchTasks() {
    //     isEmpty();

    // }

    //
    // private void applyFilters() {
    //     isEmpty();
    // }

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

    // private boolean isValidInt(int value) {
    //     return value >= 1 && value <= 8; //valid menu choices
    // }

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
