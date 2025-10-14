import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import TaskClasses.*;
import DateClasses.Date;
import TimeClasses.*;
import java.util.*;

public class TaskDAOTest {
    private TaskDAO dao;

    @BeforeEach
    void setup() {
        dao = new TaskDAO();
        dao.clearAll();
    }

    @Test
    void testSaveAndFindTask() {
        Task t = new Task("DBTest", new Category(TaskCategory.WORK), new Time(), new Date());
        dao.save(t);
        assertTrue(t.getTaskId() > 0);
        Task fetched = dao.findById(t.getTaskId());
        assertNotNull(fetched);
        assertEquals("DBTest", fetched.getName());
    }

    @Test
    void testUpdateAndDeleteTask() {
        Task t = new Task("ToUpdate", new Category(TaskCategory.WORK), new Time(), new Date());
        dao.save(t);
        t.setName("Updated");
        assertTrue(dao.update(t));
        Task fetched = dao.findById(t.getTaskId());
        assertEquals("Updated", fetched.getName());
        assertTrue(dao.delete(t.getTaskId()));
        assertNull(dao.findById(t.getTaskId()));
    }

    @Test
    void testUserMapping() {
        Task t = new Task("UserMap", new Category(TaskCategory.WORK), new Time(), new Date());
        User u = new User("Bob");
        t.addUser(u);
        dao.saveUser(u);
        dao.save(t);
        Task fetched = dao.findById(t.getTaskId());
        assertEquals(1, fetched.getUsers().size());
        assertEquals("Bob", fetched.getUsers().get(0).getName());
    }
}