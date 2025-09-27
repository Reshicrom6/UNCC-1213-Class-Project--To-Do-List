import TaskClasses.*;
import DateClasses.*;
import TimeClasses.*;

import java.util.List;

public class ToDoListExample {
    public static void main(String[] args) {

        ToDoList myList = new ToDoList("My Example List");

        // Categories
        Category schoolCategory = new Category(TaskCategory.SCHOOL);
        Category workCategory   = new Category(TaskCategory.WORK);
        Category otherCategory  = new Category(TaskCategory.OTHER);

        // Event (occurrence) date/time for a presentation
        Date eventDate = new Date(2, Month.OCTOBER, 2025);
        Time eventTime = new Time(10, 15, ClockType.TWELVE_HOUR, HourPeriod.AM);

        // Another task (meeting) with same occurrence and due at start time
        Date meetingDate = new Date(5, Month.OCTOBER, 2025);
        Time meetingTime = new Time(9, 0, ClockType.TWELVE_HOUR, HourPeriod.AM);

        // Third task (call) occurs later; soft deadline earlier same day
        Date callDate = new Date(12, Month.OCTOBER, 2025);
        Time callTime = new Time(11, 15, ClockType.TWELVE_HOUR, HourPeriod.AM);

        // Create tasks (name, category, deadline (due by), occurrence time, occurrence date)
        Task prepTask    = new Task("Prepare Presentation", schoolCategory, eventTime, eventDate);
        Task meetingTask = new Task("Team Status Meeting", workCategory, meetingTime, meetingDate);
        Task callTask    = new Task("Advisor Call", otherCategory, callTime, callDate);

        myList.addTask(prepTask);
        myList.addTask(meetingTask);
        myList.addTask(callTask);

        // Basic display (assumes Task.toString distinguishes deadline vs occurrence)
        System.out.println("All tasks:");
        for (Task t : myList.getTasks()) {
            System.out.println(t);
        }
    }
}