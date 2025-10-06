import TaskClasses.*;
import java.util.List;

/**
 * Data Access Object interface for Task operations.
 * This interface defines the contract for database operations
 * that will be implemented when database integration is added.
 *
 * @author  Jed Duncan  
 * @version Oct 6, 2025
 */
public interface TaskDAOInterface {
    
    //CRUD operations
    Task save(Task task);           //Create/Update - returns task with assigned ID
    Task findById(int taskId);      //Read by ID
    List<Task> findAll();          //Read all
    List<Task> findByName(String name);        //Read by name
    List<Task> findByCategory(Category category); //Read by category
    boolean update(Task task);      //Update existing task
    boolean delete(int taskId);     //Delete by ID
    
    //User operations
    User saveUser(User user);
    List<User> findAllUsers();
    User findUserById(int userId);
    
    //Utility operations
    void clearAll();               //For testing
    int getTaskCount();           //Get total count
}
