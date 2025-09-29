/**
 * Example program demonstrating the usage of the ToDoList system.
 * This class shows how to create tasks with different categories, dates, times,
 * and demonstrates basic operations like adding tasks and displaying the list.
 *
 * @author  Jed Duncan
 * @version Sep 29, 2025
 */
import TaskClasses.*;
import DateClasses.*;
import TimeClasses.*;

public class ToDoListExample {
    public static void main(String[] args) {
        //create a new to-do list with a custom name
        ToDoList myList = new ToDoList("My Example List");

        //create different categories for task organization
        Category schoolCategory = new Category(TaskCategory.SCHOOL);
        Category workCategory   = new Category(TaskCategory.WORK);
        Category otherCategory  = new Category(TaskCategory.OTHER);

        //create dates and times for various tasks
        //presentation task - event date/time for October 2nd at 10:15 AM
        Date eventDate = new Date(2, Month.OCTOBER, 2025);
        Time eventTime = new Time(10, 15, ClockType.TWELVE_HOUR, HourPeriod.AM);

        //meeting task - scheduled for October 5th at 9:00 AM
        Date meetingDate = new Date(5, Month.OCTOBER, 2025);
        Time meetingTime = new Time(9, 0, ClockType.TWELVE_HOUR, HourPeriod.AM);

        //advisor call - scheduled for October 12th at 11:15 AM
        Date callDate = new Date(12, Month.OCTOBER, 2025);
        Time callTime = new Time(11, 15, ClockType.TWELVE_HOUR, HourPeriod.AM);

        //create tasks with name, category, time, and date
        Task prepTask    = new Task("Prepare Presentation", schoolCategory, eventTime, eventDate);
        Task meetingTask = new Task("Team Status Meeting", workCategory, meetingTime, meetingDate);
        Task callTask    = new Task("Advisor Call", otherCategory, callTime, callDate);

        //add all tasks to the to-do list
        myList.addTask(prepTask);
        myList.addTask(meetingTask);
        myList.addTask(callTask);

        //display all tasks in the list
        System.out.println("All tasks:");
        for (Task t : myList.getTasks()) {
            System.out.println(t); //uses each task's toString() method for formatting
        }
    }
}