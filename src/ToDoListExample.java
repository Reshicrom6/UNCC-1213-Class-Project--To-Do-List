
/**
 * Example entry point that launches the console UI backed by the SQLite DAO.
 */
public class ToDoListExample {
    public static void main(String[] args) {
        ToDoListConsoleUI ui = new ToDoListConsoleUI(new TaskDAO());
        ui.run();
    }
}