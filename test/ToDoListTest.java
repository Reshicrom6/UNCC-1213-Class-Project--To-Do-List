import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import TaskClasses.*;
import DateClasses.*;
import TimeClasses.*;

public class ToDoListTest {
    @Test
    void testAddAndRemoveTask() {
        ToDoList list = new ToDoList();
        Task t = new Task("Test", new Category(TaskCategory.WORK), new Time(), new Date());
        list.addTask(t);
        assertEquals(1, list.getTasks().size());
        assertEquals("Test", list.getTasks().get(0).getName());
        list.removeTask(t);
        assertEquals(0, list.getTasks().size());
    }

    @Test
    void testEditTask() {
        ToDoList list = new ToDoList();
        Task t = new Task("Test", new Category(TaskCategory.WORK), new Time(), new Date());
        list.addTask(t);
        list.editTask(0, "NewName", "Desc", null, null);
        assertEquals("NewName", list.getTasks().get(0).getName());
        assertEquals("Desc", list.getTasks().get(0).getDescription());
    }

    @Test
    void testFindTaskByName() {
        ToDoList list = new ToDoList();
        Task t = new Task("FindMe", new Category(TaskCategory.WORK), new Time(), new Date());
        list.addTask(t);
        assertNotNull(list.findTaskByName("FindMe"));
        assertNull(list.findTaskByName("NotThere"));
    }
}