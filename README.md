# ITSC 1213 Project
 Project for UNCC class ITSC 1213

## Database integration (SQLite via JDBC)

This project includes a minimal JDBC setup to persist tasks and users using a local SQLite database file (todo.db).

What was added:
- `src/DatabaseConnector.java`: loads `config/db.properties`, initializes the schema, and provides JDBC connections.
- `src/TaskDAO.java` and `src/TaskDAOInterface.java`: CRUD operations for tasks and users.
- `config/db.properties`: configuration file with a default `jdbc.url`.

Setup:
1. Download the SQLite JDBC driver JAR (e.g., `sqlite-jdbc-3.45.2.0.jar`) and place it in the `lib/` folder.
2. Ensure `config/db.properties` exists. The default already points to `jdbc:sqlite:todo.db`.

Build and run (PowerShell):
```powershell
# Compile (adjust jar name if different)
javac -cp .;lib/sqlite-jdbc-3.45.2.0.jar src/**/*.java

# Run an example that touches the DAO (you can wire DAO into your UI next)
java -cp .;src;lib/sqlite-jdbc-3.45.2.0.jar ToDoListExample
```

Switching databases:
- Update `config/db.properties` with your JDBC URL and credentials.
- Add the appropriate JDBC driver JAR to `lib/` and include it on the classpath when compiling/running.
