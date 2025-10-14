import TaskClasses.*;
import DateClasses.*;
import TimeClasses.*;

public class DaoSmokeTest {
    public static void main(String[] args) {
        TaskDAO dao = new TaskDAO();

        // Create a sample task
        Category cat = new Category(TaskCategory.WORK);
        Date date = new Date(14, Month.OCTOBER, 2025);
        Time time = new Time(9, 30);
        Task t = new Task("DAO Test Task", cat, time, date, false);
        t.setDescription("Created via DaoSmokeTest");
        t.addUser(new User("Alice"));
        t.addUser(new User("Bob"));

        // Save
        Task saved = dao.save(t);
        System.out.println("Saved task ID: " + (saved != null ? saved.getTaskId() : -1));

        // Find by ID
        Task fetched = dao.findById(saved.getTaskId());
        System.out.println("Fetched by ID:\n" + fetched);

        // Count
        System.out.println("Total tasks in DB: " + dao.getTaskCount());

        // List
        System.out.println("All tasks:");
        for (Task task : dao.findAll()) {
            System.out.println(task);
        }

        // Update
        fetched.setComplete(true);
        dao.update(fetched);
        System.out.println("Updated completion -> " + dao.findById(fetched.getTaskId()).completed());

        // Delete
        boolean deleted = dao.delete(fetched.getTaskId());
        System.out.println("Deleted: " + deleted);
        System.out.println("Total tasks after delete: " + dao.getTaskCount());
    }
}
