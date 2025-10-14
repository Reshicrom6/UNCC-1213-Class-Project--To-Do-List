import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import TaskClasses.*;
import DateClasses.*;
import TimeClasses.*;

public class TaskTest {
    @Test
    void testTaskFields() {
        Category cat = new Category(TaskCategory.WORK);
        Date date = new Date(1, Month.JANUARY, 2025);
        Time time = new Time(10, 0);
        Task t = new Task("Name", cat, time, date, false);
        t.setDescription("desc");
        assertEquals("Name", t.getName());
        assertEquals(cat.getCategoryType(), t.getCategory().getCategoryType());
        assertEquals("desc", t.getDescription());
        assertFalse(t.completed());
    }

    @Test
    void testUserAssignment() {
        Task t = new Task();
        User u = new User("Alice");
        t.addUser(u);
        assertTrue(t.getUsers().contains(u));
        t.removeUser(u);
        assertFalse(t.getUsers().contains(u));
    }
}