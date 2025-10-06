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
            displayMenu();
            var choice = getIntInput("Enter your choice: ");
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
                    viewAllTasks();
                    break;
                case 5:
                    markTaskComplete();
                    break;
                case 6:
                    searchTasks();
                    break;  
                case 7:
                    applyFilters();
                    break;
                case 8:
                    System.out.println("Exiting the To-Do List Manager. Goodbye!");
                    return; //exit the program
            }
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

    private String getStringInput(String prompt) {
        scnr.nextLine(); //clear buffer
        System.out.println(prompt);
        return scnr.nextLine();
        
    }

    private int getIntInput(String prompt) {
        scnr.nextLine(); //clear buffer
        System.out.println(prompt);
        var value = Integer.parseInt(scnr.nextLine()); 
        while (!isValidInt(value)) {
            System.out.println("Invalid input. Please enter a number between 1 and 8.");
            value = Integer.parseInt(scnr.nextLine());
        }
        return value;
    }
    //
    private boolean isValidInt(int value) {
        return value >= 1 && value <= 8; //valid menu choices
    }

    private boolean getBooleanInput(String prompt) {
        scnr.nextLine(); //clear buffer
        System.out.println(prompt + " (y/n): ");
        var input = scnr.nextLine().trim().toLowerCase();
        while (!input.equals("y") && !input.equals("n")) {
            System.out.println("Invalid input. Please enter 'y' or 'n'.");
            input = scnr.nextLine().trim().toLowerCase();
        }
        return input.equals("y");
    }
}
