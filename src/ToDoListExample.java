/**
 * Example program demonstrating basic SQLite persistence using TaskDAO.
 *
 * It will:
 *  - Clear existing demo data (using dao.clearAll())
 *  - Create a couple of users and tasks
 *  - Save tasks to the database (auto-creating tables on first run)
 *  - Query tasks back and print them
 *  - Update a task and show the result
 */
import TaskClasses.*;
import DateClasses.Date;
import DateClasses.Month;
import TimeClasses.*;

import java.util.List;

public class ToDoListExample {
    public static void main(String[] args) {
        TaskDAO dao = new TaskDAO();

        // Start fresh for the example
        dao.clearAll();

        // Create some users and persist them (saveUser upserts by name)
        User alice = new User("Alice");
        User bob   = new User("Bob");
        dao.saveUser(alice);
        dao.saveUser(bob);

        // Categories
        Category work   = new Category(TaskCategory.WORK);
        Category school = new Category(TaskCategory.SCHOOL);

        // Dates/times
        Date today = new Date(16, Month.OCTOBER, 2025);
        Time morning = new Time(9, 0, ClockType.TWELVE_HOUR, HourPeriod.AM);
        Time afternoon = new Time(2, 30, ClockType.TWELVE_HOUR, HourPeriod.PM);

        // Create tasks
        Task report = new Task("Write Report", work, morning, today);
        report.setDescription("Draft quarterly report");
        report.addUser(alice);

        Task study = new Task("Study for Exam", school, afternoon, today);
        study.setDescription("Chapters 5-7");
        study.addUser(bob);

        // Save tasks (INSERT). IDs are assigned on save.
        dao.save(report);
        dao.save(study);

        System.out.println("All tasks after insert:");
        printTasks(dao.findAll());

        // Update a task
        report.setName("Write Final Report");
        report.setComplete(true);
        dao.update(report);

        System.out.println("\nTask fetched by ID after update:");
        Task fetched = dao.findById(report.getTaskId());
        printTask(fetched);

        System.out.println("\nTasks matching name 'final':");
        printTasks(dao.findByName("final"));

        System.out.println("\nTasks in WORK category:");
        printTasks(dao.findByCategory(work));

        System.out.println("\nTotal task count: " + dao.getTaskCount());
    }

    private static void printTasks(List<Task> tasks) {
        if (tasks == null || tasks.isEmpty()) {
            System.out.println("(none)");
            return;
        }
        for (Task t : tasks) {
            printTask(t);
            System.out.println();
        }
    }

    private static void printTask(Task t) {
        if (t == null) {
            System.out.println("(null)");
            return;
        }
        System.out.println("ID: " + t.getTaskId());
        System.out.println(t.toString());
        if (t.getUsers() != null && !t.getUsers().isEmpty()) {
            System.out.print("Assigned Users: ");
            for (int i = 0; i < t.getUsers().size(); i++) {
                System.out.print(t.getUsers().get(i).getName());
                if (i < t.getUsers().size() - 1) System.out.print(", ");
            }
            System.out.println();
        }
    }
}