import TaskClasses.*;
import DateClasses.*;
import TimeClasses.*;
public class ToDoListExample {
    public static void main(String[] args) {
        // Create a to-do list
        ToDoList myList = new ToDoList("My Example List");

        // Create a deadline (e.g., Sep 30, 2025, 2:30 PM)
        Date dueDate = new Date(30, Month.SEPTEMBER, 2025);
        Time dueTime = new Time(2, 30, ClockType.TWELVE_HOUR, HourPeriod.PM);
        deadLine deadline = new deadLine(dueDate, dueTime);

        // Create a category
        Category category = new Category(TaskCategory.OTHER);

        // Create a task
        Task task = new Task("Finish Java Project", category, deadline);

        // Add the task to the list
        myList.addTask(task);

        // Print the to-do list
        System.out.println(myList);
    }
}